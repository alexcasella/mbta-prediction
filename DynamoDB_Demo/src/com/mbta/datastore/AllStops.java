package com.mbta.datastore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.Tables;
import com.mbta.api.Routes;
import com.mbta.api.StopsByRoute;
import com.mbta.dbmapper.AllStopsMapper;
import com.mbta.entity.AllRoutesEntity;
import com.mbta.entity.DirectionEntity;
import com.mbta.entity.ModeEntity;
import com.mbta.entity.RouteEntity;
import com.mbta.entity.StopEntity;
import com.mbta.entity.StopsByRouteEntity;
import com.mbta.util.TableInfo;

public class AllStops extends Thread {

	static AmazonDynamoDB dynamo = new AmazonDynamoDBClient(
			new ProfileCredentialsProvider("Andy").getCredentials());

	static DynamoDB dynamoDB = new DynamoDB(dynamo);

	static String tableName = "All-Stops-Table";

	static DynamoDBMapper mapper = new DynamoDBMapper(dynamo);

	private static final Logger logger = Logger.getLogger(AllStops.class
			.getName());

	private static void init() throws Exception {

		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		dynamo.setRegion(usWest2);
	}

	public void run() {
		while (true) {
			try {
				init();

				if (!Tables.doesTableExist(dynamo, tableName)) {
					logger.log(Level.INFO, tableName
							+ " not exist, creat table and activate...");
					createTable();
				}
				logger.log(Level.INFO, tableName
						+ " already exist, clean table and create items...");
				scanAndDeleteItems();
				insertItems();

				Thread.sleep(1000L * 60L * 60L * 24L * 7L);
				// For debug 
				// Thread.sleep(1000L * 15L);

			} catch (InterruptedException ie) {
				logger.error(ie.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}

	}

	private static void insertItems() {

		try {
			// get the API stop data
			AllRoutesEntity allRoutes = Routes.getAllRoutes();

			long now = new Date().getTime() / 1000;
			String last_modified = Long.toString(now);
			
			int counter = 0;

			for (ModeEntity modeFromAllRoute : allRoutes.getMode()) {
				if (modeFromAllRoute.getRoute_type().equals("0")
						|| modeFromAllRoute.getRoute_type().equals("1")) {

					for (RouteEntity routeFromAllRoute : modeFromAllRoute
							.getRoute()) {

						StopsByRouteEntity stop_list = StopsByRoute
								.getStop(routeFromAllRoute.getRoute_id());
						for (DirectionEntity directionFromSBR : stop_list
								.getDirection()) {

							for (StopEntity stop : directionFromSBR.getStop()) {
								AllStopsMapper asm = new AllStopsMapper();
								asm.setStop_id(stop.getStop_id());
								asm.setStop_name(stop.getStop_name());
								asm.setDirection_id(directionFromSBR
										.getDirection_id());
								asm.setRoute_id(routeFromAllRoute.getRoute_id());
								asm.setRoute_name(routeFromAllRoute
										.getRoute_name());
								asm.setLast_modified(last_modified);

								counter++;
								mapper.save(asm);
							}
						}
					}
				}
			}
			logger.info(counter + " items in " + tableName + " saved.");

		} catch (Exception e) {

			logger.error(e.getMessage());
		}
	}

	private static void scanAndDeleteItems() {
		long now = new Date().getTime() / 1000;
		long oneWeekAgoSec = (now - (7L * 24L * 60L * 60L));
		String oneWeekAgo = Long.toString(oneWeekAgoSec);

		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(oneWeekAgo));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("last_modified < :val1")
				.withExpressionAttributeValues(eav);

		List<AllStopsMapper> allStops = mapper.scan(AllStopsMapper.class,
				scanExpression);
		for (AllStopsMapper stop : allStops) {
			mapper.delete(stop);
		}
		logger.info(allStops.size() + " items in " + tableName + " cleaned.");

	}

	private static void createTable() {

		try {
			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("stop_id")
					.withKeyType(KeyType.HASH)); // Partition key

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("stop_id").withAttributeType("S"));

			keySchema.add(new KeySchemaElement().withAttributeName(
					"direction_id").withKeyType(KeyType.RANGE)); // Sort key

			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("direction_id").withAttributeType("S"));

			CreateTableRequest request = new CreateTableRequest()
					.withTableName(tableName)
					.withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions)
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(
									5L).withWriteCapacityUnits(6L));

			logger.info("Issuing CreateTable request for "
					+ tableName);
			Table table = dynamoDB.createTable(request);

			// Wait for it to become active
			System.out.println("Waiting for " + tableName
					+ " to be created...this may take a while...");

			table.waitForActive();
			TableInfo.getTableInformation(dynamoDB, tableName);

		} catch (Exception e) {
			System.err.println("CreateTable request failed for " + tableName);
			logger.error(e.getMessage());
		}
	}

}
