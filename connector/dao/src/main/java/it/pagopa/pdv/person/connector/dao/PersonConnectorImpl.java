package it.pagopa.pdv.person.connector.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperTableModel;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder;
import com.amazonaws.services.dynamodbv2.xspec.UpdateAction;
import it.pagopa.pdv.person.connector.PersonConnector;
import it.pagopa.pdv.person.connector.dao.model.PersonDetails;
import it.pagopa.pdv.person.connector.dao.model.PersonId;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.*;

import static com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder.*;

@Slf4j
@Service
public class PersonConnectorImpl implements PersonConnector {

    public static final String TABLE_NAME = "Person";
    public static final List<String> M_FIELD_WHITELIST = List.of(PersonDetails.Fields.workContacts + ".");

    private final DynamoDBMapper dynamoDBMapper;
    private final DynamoDB dynamoDB;
    private final Table table;
    private final DynamoDBMapperTableModel<PersonDetails> personDetailsModel;


    @Autowired
    public PersonConnectorImpl(DynamoDBMapper dynamoDBMapper, DynamoDB dynamoDB) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDB = dynamoDB;
        table = dynamoDB.getTable(TABLE_NAME);
        personDetailsModel = dynamoDBMapper.getTableModel(PersonDetails.class);
    }


    @Override
    public Optional<PersonDetailsOperations> findByGlobalId(String id) {
        return Optional.ofNullable(dynamoDBMapper.load(PersonDetails.class, id));
    }


    @Override
    public Optional<PersonDetailsOperations> findByNamespacedId(String id) {
        String globalId;
        Index index = table.getIndex("gsi_namespaced_id");
        ItemCollection<QueryOutcome> itemCollection = index.query(new QuerySpec()
                .withHashKey(PersonId.Fields.namespacedId, id)
                .withProjectionExpression("PK"));
        Iterator<Page<Item, QueryOutcome>> iterator = itemCollection.pages().iterator();
        if (iterator.hasNext()) {
            Page<Item, QueryOutcome> page = iterator.next();
            if (page.getLowLevelResult().getItems().size() == 0) {
                throw new RuntimeException("Not Found");//FIXME: change exception
            } else if (page.getLowLevelResult().getItems().size() > 1) {
                throw new RuntimeException("Too many results");//FIXME: change exception
            } else {
                globalId = page.getLowLevelResult().getItems().get(0).getString("PK");
            }
        } else {
            throw new RuntimeException("Not Found");//FIXME: change exception
        }
        return findByGlobalId(globalId);
    }


    @Override
    public void save(PersonDetailsOperations personDetails) {
        dynamoDBMapper.save(new PersonDetails(personDetails));
    }


    @Override
    public void save(PersonIdOperations personId) {
        dynamoDBMapper.save(new PersonId(personId));
    }


    @Override
    public void patch(PersonDetailsOperations personDetails) {
        PersonDetails person = new PersonDetails(personDetails);
        Map<String, AttributeValue> attributeValueMap = personDetailsModel.convert(person);
        personDetailsModel.convertKey(person).keySet().forEach(attributeValueMap::remove);
        PrimaryKey primaryKey = new PrimaryKey(personDetailsModel.hashKey().name(),
                personDetailsModel.hashKey().get(person),
                personDetailsModel.rangeKey().name(),
                personDetailsModel.rangeKey().get(person));
        if (attributeValueMap.isEmpty()) {
            table.putItem(new Item().withPrimaryKey(primaryKey));
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
                System.out.println();
            }
        }
    }


    private void createParentNodes(PrimaryKey primaryKey, Deque<UpdateAction> backwardsMissingNodes) {
        Deque<UpdateAction> forwardsMissingNodes = createParentNodesBackwards(primaryKey, backwardsMissingNodes);
        createParentNodesForwards(primaryKey, forwardsMissingNodes);
    }

    private void createParentNodesForwards(PrimaryKey primaryKey, Deque<UpdateAction> forwardsMissingNodes) {
        while (forwardsMissingNodes.peek() != null) {
            // try to create parent node (forwards)
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
        while (backwardsMissingNodes.peek() != null) {
            // try to create parent node (backwards)
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
        dynamoDBMapper.delete(new PersonDetails(id));
    }


    @Override
    public void deleteById(String id, String namespace) {
        PersonId personId = new PersonId();
        personId.setGlobalId(id);
        personId.setNamespace(namespace);
        dynamoDBMapper.delete(personId);
    }

}
