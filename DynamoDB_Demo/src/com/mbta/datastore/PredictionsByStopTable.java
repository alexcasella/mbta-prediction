package com.mbta.datastore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;

/**
 * This sample demonstrates how to perform a few simple operations with the
 * Amazon DynamoDB service.
 */
@SuppressWarnings("deprecation")
public class PredictionsByStopTable {

	static AmazonDynamoDBClient dynamoDB;

	private static void init() throws Exception {

		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider("default")
					.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. "
							+ "Please make sure that your credentials file is at the correct "
							+ "location (/Users/andy/.aws/credentials), and is in valid format.",
					e);
		}
		dynamoDB = new AmazonDynamoDBClient(credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		dynamoDB.setRegion(usWest2);
	}

	public static void main(String[] args) throws Exception {
		init();

		try {
			String tableName = "Predictions-By-Stop-Table";

			// Create table if it does not exist yet
			if (Tables.doesTableExist(dynamoDB, tableName)) {
				System.out.println("Table " + tableName + " is already ACTIVE");
			} else {
				// Create a table with a primary hash key named 'stop_id', which
				// holds a string
				ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
				keySchema.add(new KeySchemaElement().withAttributeName(
						"stop_id").withKeyType(KeyType.HASH)); // Partition key

				ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
				attributeDefinitions.add(new AttributeDefinition()
						.withAttributeName("stop_id").withAttributeType("S"));

				keySchema.add(new KeySchemaElement().withAttributeName(
						"direction_id").withKeyType(KeyType.RANGE)); // Sort key

				attributeDefinitions.add(new AttributeDefinition()
						.withAttributeName("direction_id").withAttributeType(
								"S"));

				CreateTableRequest createTableRequest = new CreateTableRequest()
						.withTableName(tableName)
						.withKeySchema(keySchema)
						.withAttributeDefinitions(attributeDefinitions)
						.withProvisionedThroughput(
								new ProvisionedThroughput()
										.withReadCapacityUnits(10L)
										.withWriteCapacityUnits(5L));
				TableDescription createdTableDescription = dynamoDB
						.createTable(createTableRequest).getTableDescription();
				System.out.println("Created Table: " + createdTableDescription);

				// Wait for it to become active
				System.out.println("Waiting for " + tableName
						+ " to become ACTIVE...");
				Tables.awaitTableToBecomeActive(dynamoDB, tableName);
			}

			// Describe our new table
			DescribeTableRequest describeTableRequest = new DescribeTableRequest()
					.withTableName(tableName);
			TableDescription tableDescription = dynamoDB.describeTable(
					describeTableRequest).getTable();
			System.out.println("Table Description: " + tableDescription);

			// Hard code to test the DB write, will use DynamoDBMapper to save,
			// retrieve, update and delete.
			// Add an item
			Map<String, AttributeValue> item = newItem("70075",
					"Park Street - to Ashmont/Braintree", "Red", "Red Line",
					"0");
			PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
			PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
			System.out.println("Result: " + putItemResult);

			// Add another item
			item = newItem("70075", "Park Street - to Ashmont/Braintree",
					"Red", "Red Line", "1");
			putItemRequest = new PutItemRequest(tableName, item);
			putItemResult = dynamoDB.putItem(putItemRequest);
			System.out.println("Result: " + putItemResult);

		} catch (AmazonServiceException ase) {
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to AWS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with AWS, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}

	private static Map<String, AttributeValue> newItem(String stop_id,
			String stop_name, String route_id, String route_name,
			String direction_id) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put("stop_id", new AttributeValue(stop_id));
		item.put("stop_name", new AttributeValue(stop_name));
		item.put("route_id", new AttributeValue(route_id));
		item.put("route_name", new AttributeValue(route_name));
		item.put("direction_id", new AttributeValue(direction_id));

		return item;
	}

}