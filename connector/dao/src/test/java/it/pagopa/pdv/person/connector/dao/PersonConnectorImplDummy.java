package it.pagopa.pdv.person.connector.dao;

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException;


public class PersonConnectorImplDummy {
    public void notThrowingProvisionedThroughputExceededException() {

    }

    public void throwingProvisionedThroughputExceededException() {
        throw new ProvisionedThroughputExceededException("ProvisionedThroughputExceededException");
    }

    public void throwingGenericException() throws Exception {
        throw new Exception("GenericException");
    }

}
