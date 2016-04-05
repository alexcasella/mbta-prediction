package com.mbta.datastore;

import java.util.ArrayList;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;
import com.amazonaws.services.dynamodbv2.util.Tables;

public class FuncTest {
	static AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient(
			new ProfileCredentialsProvider("default").getCredentials());

	public static void main(String[] args) throws Exception {

		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		dynamoDB.setRegion(usWest2);

		String tableName = "Example-Table-from-AWS-Doc";

		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName(
				"Id").withAttributeType("N"));

		ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
		keySchema.add(new KeySchemaElement().withAttributeName("Id")
				.withKeyType(KeyType.HASH));

		CreateTableRequest request = new CreateTableRequest()
				.withTableName(tableName);

		request.withKeySchema(keySchema)
				.withAttributeDefinitions(attributeDefinitions)
				.withProvisionedThroughput(
						new ProvisionedThroughput().withReadCapacityUnits(5L)
								.withWriteCapacityUnits(6L));

		TableDescription createdTableDescription = dynamoDB
				.createTable(request).getTableDescription();
		System.out.println("Created Table: " + createdTableDescription);

		waitForTableToBecomeAvailable(tableName);
		System.out.println("Done!");

		/*
		 * Table table = dynamoDB.getTable(tableName);
		 * 
		 * try { System.out.println("Issuing DeleteTable request for " +
		 * tableName); table.delete();
		 * 
		 * System.out.println("Waiting for " + tableName +
		 * " to be deleted...this may take a while...");
		 * 
		 * table.waitForDelete(); System.out.println("Done"); } catch (Exception
		 * e) { System.err.println("DeleteTable request failed for " +
		 * tableName); System.err.println(e.getMessage()); }
		 */
	}

	protected static void waitForTableToBecomeAvailable(String tableName)
			throws InterruptedException {

		System.out.println("Waiting for " + tableName + " to become ACTIVE...");

		long startTime = System.currentTimeMillis();
		long endTime = startTime + (10 * 60 * 1000);
		while (System.currentTimeMillis() < endTime) {
			Thread.sleep(1000 * 20);
			try {
				DescribeTableRequest request = new DescribeTableRequest()
						.withTableName(tableName);
				TableDescription table = dynamoDB.describeTable(request)
						.getTable();
				if (table == null)
					continue;

				String tableStatus = table.getTableStatus();
				System.out.println("  - current state: " + tableStatus);
				if (tableStatus.equals(TableStatus.ACTIVE.toString()))
					return;
			} catch (AmazonServiceException ase) {
				if (!ase.getErrorCode().equalsIgnoreCase(
						"ResourceNotFoundException"))
					throw ase;
			}
		}

		throw new RuntimeException("Table " + tableName + " never went active");
	}

}
