package it.pagopa.pdv.person.connector.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperTableModel;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.BatchWriteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
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

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

import static com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder.*;

@Slf4j
@Service
public class PersonConnectorImpl implements PersonConnector {

    public static final String TABLE_NAME = "Person";

    private final DynamoDBMapper dynamoDBMapper;
    private final DynamoDB dynamoDB;
    private final Table table;


    @Autowired
    public PersonConnectorImpl(DynamoDBMapper dynamoDBMapper, DynamoDB dynamoDB) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDB = dynamoDB;
        table = dynamoDB.getTable(TABLE_NAME);
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
//        dynamoDBMapper.save(person, new DynamoDBMapperConfig.Builder()
//                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
////                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.APPEND_SET)
//                .build());

        final Map<String, AttributeValueUpdate> updateValues = new HashMap<>();
        DynamoDBMapperTableModel<PersonDetails> tableModel = dynamoDBMapper.getTableModel(PersonDetails.class);
        Map<String, AttributeValue> attributeValueMap = tableModel.convert(person);
        tableModel.convertKey(person).keySet().forEach(attributeValueMap::remove);

        ExpressionSpecBuilder expressionSpecBuilder = new ExpressionSpecBuilder();
        test2(expressionSpecBuilder, attributeValueMap, "");
        try {
            table.updateItem(new UpdateItemSpec()
                    .withPrimaryKey(tableModel.hashKey().name(), tableModel.hashKey().get(person), tableModel.rangeKey().name(), tableModel.rangeKey().get(person))
                    .withExpressionSpec(expressionSpecBuilder.buildForUpdate()));
        } catch (AmazonDynamoDBException e) {
            if ("ValidationException".equals(e.getErrorCode())) {

            }
            System.out.println();
        }
    }


    private void test2(ExpressionSpecBuilder expressionBuilder, Map<String, AttributeValue> attributeValueMap, String attributeNamePrefix) {
        attributeValueMap.forEach((attributeName, attributeValue) -> {
            if (attributeValue.getBS() != null) {
                expressionBuilder.addUpdate(BS(attributeNamePrefix + attributeName).append(attributeValue.getBS().toArray(new ByteBuffer[attributeValue.getBS().size()])));
            } else if (attributeValue.getNS() != null) {
                throw new IllegalArgumentException();
            } else if (attributeValue.getSS() != null) {
                expressionBuilder.addUpdate(SS(attributeNamePrefix + attributeName).append(new HashSet<>(attributeValue.getSS())));
            } else if (attributeValue.getM() != null) {
//                expressionBuilder.addUpdate(M(attributeNamePrefix + attributeName).set(M(attributeNamePrefix + attributeName).ifNotExists(Map.of())));
                test2(expressionBuilder, attributeValue.getM(), attributeNamePrefix + attributeName + ".");
            } else {
                expressionBuilder.addUpdate(S(attributeNamePrefix + attributeName).set(attributeValue.getS()));
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
