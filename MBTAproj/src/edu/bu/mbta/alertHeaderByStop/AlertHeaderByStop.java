package edu.bu.mbta.alertHeaderByStop;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import edu.bu.mbta.predictionTable.createTable;
import edu.bu.mbta.predictionTable.updateAlertHeaders;
import edu.bu.mbta.predictionTable.updateAllAlerts;

public class AlertHeaderByStop extends Thread{
	
	AmazonDynamoDBClient dynamoDB;
	public AlertHeaderByStop(AmazonDynamoDBClient DB) {
		dynamoDB = DB;
	}
	    
	public void run() {
		try {
			execute(dynamoDB);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void execute(AmazonDynamoDBClient dynamoDB) throws Exception {
		String tableName = "AlertHeaderByStop";		
		createTable.create(dynamoDB, tableName);
		
		while (true) {
			updateAlertHeaders.updateAlertHeader(dynamoDB,tableName);
			Thread.sleep(1000L * 60L * 60L * 3L);
		}
	}
	

}
