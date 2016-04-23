package com.mbta.datastore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.mbta.api.Alerts;
import com.mbta.dbmapper.AllAlertsMapper;
import com.mbta.entity.AlertEntity;
import com.mbta.entity.AllAlertsEntity;
import com.mbta.entity.EffectPeriodEntity;
import com.mbta.util.TableInfo;

public class AllAlerts extends Thread {

	static AmazonDynamoDB dynamo = new AmazonDynamoDBClient(
			new ProfileCredentialsProvider("Andy").getCredentials());

	static DynamoDB dynamoDB = new DynamoDB(dynamo);

	static String tableName = "All-Alerts-Table";

	static DynamoDBMapper mapper = new DynamoDBMapper(dynamo);

	private static final Logger logger = Logger.getLogger(AllAlerts.class
			.getName());

	private static void init() throws Exception {
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		dynamo.setRegion(usWest2);
	}

	public void run() {
		while (true) {
			try {

				init();

				Calendar rightNow = Calendar.getInstance();
				int hour = rightNow.get(Calendar.HOUR_OF_DAY);
				if (hour >= 5 || hour <= 2) {

					if (!Tables.doesTableExist(dynamo, tableName)) {
						logger.info(tableName
								+ " not exist, creat table and activate...");
						createTable();
					}
					logger.info(tableName
							+ " already exist, clean table and create items...");
					scanAndDeleteItems();
					insertItems();

					Thread.sleep(1000L * 60L * 15L);
					// For debug
					// System.out.println(minute + " in alerts not pause");
					// Thread.sleep(1000L * 30L);

				}

				else {
					// sleep an hour
					Thread.sleep(1000L * 60L * 60L);
				}

			} catch (InterruptedException ie) {
				logger.error(ie.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	private static void scanAndDeleteItems() {
		long now = new Date().getTime() / 1000;
		long twoWeekAgoSec = (now - (14L * 24L * 60L * 60L));
		String oneWeekAgo = Long.toString(twoWeekAgoSec);

		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(oneWeekAgo));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("created_date < :val1")
				.withExpressionAttributeValues(eav);

		List<AllAlertsMapper> allAlerts = mapper.scan(AllAlertsMapper.class,
				scanExpression);
		for (AllAlertsMapper alert : allAlerts) {
			mapper.delete(alert);
		}
		logger.info(allAlerts.size() + " items in " + tableName + " cleaned.");

	}

	private static void insertItems() {
		try {

			// get the API stop data
			AllAlertsEntity allAlerts = Alerts.getAllAlerts(true, true);

			long now = new Date().getTime() / 1000;
			String created_date = Long.toString(now);

			for (AlertEntity alert : allAlerts.getAlerts()) {

				AllAlertsMapper aam = new AllAlertsMapper();
				aam.setAlert_id(Integer.toString(alert.getAlert_id()));
				aam.setEffect_name(alert.getEffect_name());
				aam.setHeader_text(alert.getHeader_text());
				aam.setSeverity(alert.getSeverity());
				aam.setService_effect_text(alert.getService_effect_text());

				Map<String, String> periods = new LinkedHashMap<String, String>();
				for (EffectPeriodEntity eachperiod : alert.getEffect_periods()) {

					if (eachperiod.getEffect_end().isEmpty()) {
						periods.put(eachperiod.getEffect_start(), "Unknown");
					} else {
						periods.put(eachperiod.getEffect_start(),
								eachperiod.getEffect_end());
					}

				}
				aam.setEffect_periods(periods);
				aam.setCreated_date(created_date);

				mapper.save(aam);

			}
			logger.info(allAlerts.getAlerts().size() + " items in " + tableName
					+ " saved.");

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private static void createTable() {
		try {
			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("alert_id")
					.withKeyType(KeyType.HASH)); // Partition key

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("alert_id").withAttributeType("S"));

			CreateTableRequest request = new CreateTableRequest()
					.withTableName(tableName)
					.withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions)
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(
									3L).withWriteCapacityUnits(8L));

			logger.info("Issuing CreateTable request for " + tableName);
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
