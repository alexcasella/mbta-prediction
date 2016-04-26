package edu.bu.mbta.predictionTable;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;

public class createTable {
	public static void create(AmazonDynamoDBClient dynamoDB, String tableName) throws Exception {
	    if (Tables.doesTableExist(dynamoDB, tableName)) {
	    	System.out.println("Table " + tableName + " is already ACTIVE");
	    } else {
		    CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
		        .withKeySchema(new KeySchemaElement().withAttributeName("stop_id").withKeyType(KeyType.HASH))
		        .withAttributeDefinitions(new AttributeDefinition().withAttributeName("stop_id").withAttributeType(ScalarAttributeType.S))
		        .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
		        TableDescription createdTableDescription = dynamoDB.createTable(createTableRequest).getTableDescription();
		    System.out.println("Created Table: " + createdTableDescription);
		
		        // Wait for it to become active
		    System.out.println("Waiting for " + tableName + " to become ACTIVE...");
		    Tables.awaitTableToBecomeActive(dynamoDB, tableName);
	    }
	    
	    DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
	    TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
	    System.out.println("Table Description: " + tableDescription);
	}
}
