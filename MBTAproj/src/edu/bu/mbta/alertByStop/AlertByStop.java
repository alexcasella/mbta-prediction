package edu.bu.mbta.alertByStop;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import edu.bu.mbta.predictionTable.createTable;
import edu.bu.mbta.predictionTable.updateAllAlerts;
import edu.bu.mbta.predictionTable.updatePrediction;

public class AlertByStop extends Thread{	

	AmazonDynamoDBClient dynamoDB;
	public AlertByStop(AmazonDynamoDBClient DB) {
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
		String tableName = "AlertsByStop";		
		createTable.create(dynamoDB, tableName);
		while (true) {
			updateAllAlerts.updateAlerts(dynamoDB,tableName);
			Thread.sleep(1000L * 60L * 60L * 3L);
		}
	}
	

}

