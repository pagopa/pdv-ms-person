package it.pagopa.pdv.person.connector.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperTableModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder;
import com.amazonaws.services.dynamodbv2.xspec.UpdateAction;
import it.pagopa.pdv.person.connector.PersonConnector;
import it.pagopa.pdv.person.connector.dao.model.PersonDetails;
import it.pagopa.pdv.person.connector.dao.model.PersonId;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.*;

import static com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder.*;

@Slf4j
@Service
public class PersonConnectorImpl implements PersonConnector {

    public static final String TABLE_NAME = "Person";
    public static final List<String> M_FIELD_WHITELIST = List.of(PersonDetails.Fields.workContacts + ".");
    private static final Marker CONFIDENTIAL_MARKER = MarkerFactory.getMarker("CONFIDENTIAL");

    private final Table table;
    private final DynamoDBMapperTableModel<PersonDetails> personDetailsModel;
    private final DynamoDBTableMapper<PersonDetails, String, String> personDetailsTableMapper;
    private final DynamoDBTableMapper<PersonId, String, String> personIdTableMapper;


    @Autowired
    public PersonConnectorImpl(DynamoDBMapper dynamoDBMapper, DynamoDB dynamoDB) {
        log.trace("Initializing {}", PersonConnectorImpl.class.getSimpleName());
        table = dynamoDB.getTable(TABLE_NAME);
        personDetailsModel = dynamoDBMapper.getTableModel(PersonDetails.class);
        personDetailsTableMapper = dynamoDBMapper.newTableMapper(PersonDetails.class);
        personIdTableMapper = dynamoDBMapper.newTableMapper(PersonId.class);
    }


    @Override
    public Optional<PersonDetailsOperations> findById(String id) {
        log.trace("[findById] start");
        log.debug("[findById] inputs: id = {}", id);
        Assert.hasText(id, "A person id is required");
        Optional<PersonDetailsOperations> personDetails = Optional.ofNullable(personDetailsTableMapper.load(id));
        log.debug(CONFIDENTIAL_MARKER, "[findById] output = {}", personDetails);
        log.trace("[findById] end");
        return personDetails;
    }


    @Override
    public Optional<String> findIdByNamespacedId(String namespacedId) {
        log.trace("[findIdByNamespacedId] start");
        log.debug("[findIdByNamespacedId] inputs: namespacedId = {}", namespacedId);
        Assert.hasText(namespacedId, "A person namespaced id is required");
        Optional<String> id = Optional.empty();
        Index index = table.getIndex("gsi_namespaced_id");
        ItemCollection<QueryOutcome> itemCollection = index.query(new QuerySpec()
                .withHashKey(PersonId.Fields.namespacedId, namespacedId)
                .withProjectionExpression(personDetailsModel.hashKey().name()));
        Iterator<Page<Item, QueryOutcome>> iterator = itemCollection.pages().iterator();
        if (iterator.hasNext()) {
            Page<Item, QueryOutcome> page = iterator.next();
            if (page.getLowLevelResult().getItems().size() == 1) {
                id = Optional.ofNullable(page.getLowLevelResult().getItems().get(0).getString(personDetailsModel.hashKey().name()));
            }
        }
        log.debug("[findIdByNamespacedId] output = {}", id);
        log.trace("[findIdByNamespacedId] end");
        return id;
    }


    @Override
    public void save(PersonIdOperations personId) {
        log.trace("[save] start");
        log.debug("[save] inputs: personId = {}", personId);
        Assert.notNull(personId, "A person id is required");
        try {
            personIdTableMapper.saveIfNotExists(new PersonId(personId));//TODO: good for performance??
        } catch (ConditionalCheckFailedException e) {
            log.debug("A PersonId with the given primary key already exists");
        }
        log.trace("[save] end");
    }


    @Override
    public void save(PersonDetailsOperations personDetails) {
        log.trace("[save] start");
        log.debug(CONFIDENTIAL_MARKER, "[save] inputs: personDetails = {}", personDetails);
        Assert.notNull(personDetails, "A person details is required");
        PersonDetails person = new PersonDetails(personDetails);
        Map<String, AttributeValue> attributeValueMap = personDetailsModel.convert(person);
        personDetailsModel.convertKey(person).keySet().forEach(attributeValueMap::remove);
        PrimaryKey primaryKey = new PrimaryKey(personDetailsModel.hashKey().name(),
                personDetailsModel.hashKey().get(person),
                personDetailsModel.rangeKey().name(),
                personDetailsModel.rangeKey().get(person));
        if (attributeValueMap.isEmpty()) {
            try {
                personDetailsTableMapper.saveIfNotExists(personDetailsModel.createKey(person.getId(), person.getType()));//TODO: good for performance??
            } catch (ConditionalCheckFailedException e) {
                log.debug("A PersonDetails with the given primary key already exists");
            }
        } else {
            ExpressionSpecBuilder expressionSpecBuilder = new ExpressionSpecBuilder();
            Deque<UpdateAction> missingNodes = new ArrayDeque<>();
            setUpdateActions(expressionSpecBuilder, missingNodes, attributeValueMap, "");
            UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                    .withPrimaryKey(primaryKey)
                    .withExpressionSpec(expressionSpecBuilder.buildForUpdate());
            try {
                table.updateItem(updateItemSpec);
            } catch (AmazonDynamoDBException e) {
                if ("ValidationException".equals(e.getErrorCode())) {
                    // create tree parent nodes
                    createParentNodes(primaryKey, missingNodes);
                    // retry failed update
                    table.updateItem(updateItemSpec);
                }
            }
        }
        log.trace("[save] end");
    }


    private void createParentNodes(PrimaryKey primaryKey, Deque<UpdateAction> backwardsMissingNodes) {
        Deque<UpdateAction> forwardsMissingNodes = createParentNodesBackwards(primaryKey, backwardsMissingNodes);
        createParentNodesForwards(primaryKey, forwardsMissingNodes);
    }


    private void createParentNodesForwards(PrimaryKey primaryKey, Deque<UpdateAction> forwardsMissingNodes) {
        // try to create parent nodes (forwards)
        while (forwardsMissingNodes.peek() != null) {
            UpdateAction updateAction = forwardsMissingNodes.pop();
            try {
                doUpdateItem(primaryKey, updateAction);
            } catch (AmazonDynamoDBException ex) {
                // do nothing
                // if the node already exists, it means that other threads have added it
            }
        }
    }


    private Deque<UpdateAction> createParentNodesBackwards(PrimaryKey primaryKey, Deque<UpdateAction> backwardsMissingNodes) {
        Deque<UpdateAction> forwardsMissingNodes = new ArrayDeque<>();
        // try to create parent nodes (backwards)
        while (backwardsMissingNodes.peek() != null) {
            UpdateAction updateAction = backwardsMissingNodes.pop();
            try {
                doUpdateItem(primaryKey, updateAction);
                break;
            } catch (AmazonDynamoDBException ex) {
                // tree parent node already exists, try with the next one
                forwardsMissingNodes.push(updateAction);
            }
        }
        return forwardsMissingNodes;
    }


    private void doUpdateItem(PrimaryKey primaryKey, UpdateAction updateAction) {
        table.updateItem(new UpdateItemSpec()
                .withPrimaryKey(primaryKey)
                .withExpressionSpec(new ExpressionSpecBuilder()
                        .addUpdate(updateAction)
                        .buildForUpdate()));
    }


    private void setUpdateActions(ExpressionSpecBuilder expressionBuilder, Deque<UpdateAction> missingNodes, Map<String, AttributeValue> attributeValueMap, String attributeNamePrefix) {
        attributeValueMap.forEach((attributeName, attributeValue) -> {
            if (attributeValue.getBS() != null) {
                expressionBuilder.addUpdate(BS(attributeNamePrefix + attributeName)
                        .append(attributeValue.getBS().toArray(new ByteBuffer[attributeValue.getBS().size()])));
            } else if (attributeValue.getNS() != null) {
                expressionBuilder.addUpdate(NS(attributeNamePrefix + attributeName)
                        .append(ItemUtils.<Set<BigDecimal>>toSimpleValue(attributeValue)));
            } else if (attributeValue.getSS() != null) {
                expressionBuilder.addUpdate(SS(attributeNamePrefix + attributeName)
                        .append(ItemUtils.<Set<String>>toSimpleValue(attributeValue)));
            } else if (attributeValue.getM() != null) {
                if (M_FIELD_WHITELIST.contains(attributeNamePrefix)) {
                    expressionBuilder.addUpdate(M(attributeNamePrefix + attributeName)
                            .set(ItemUtils.toSimpleMapValue(attributeValue.getM())));
                } else {
                    missingNodes.push(M(attributeNamePrefix + attributeName)
                            .set(M(attributeNamePrefix + attributeName).ifNotExists(Map.of())));
                    setUpdateActions(expressionBuilder, missingNodes, attributeValue.getM(), attributeNamePrefix + attributeName + ".");
                }
            } else {
                expressionBuilder.addUpdate(S(attributeNamePrefix + attributeName)
                        .set(attributeValue.getS()));
            }
        });
    }


    @Override
    public void deleteById(String id) {
        log.trace("[deleteById] start");
        log.debug("[deleteById] inputs: id = {}", id);
        Assert.hasText(id, "A person id is required");
        personDetailsTableMapper.delete(new PersonDetails(id));
        log.trace("[deleteById] end");
    }


    @Override
    public void deleteById(String id, String namespace) {
        log.trace("[deleteById] start");
        log.debug("[deleteById] inputs: id = {}, namespace = {}", id, namespace);
        Assert.hasText(id, "A person id is required");
        Assert.hasText(id, "A namespace is required");
        PersonId personId = new PersonId();
        personId.setGlobalId(id);
        personId.setNamespace(namespace);
        personIdTableMapper.delete(personId);
        log.trace("[deleteById] end");
    }

}
