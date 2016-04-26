package edu.bu.mbta.predictionByRoute;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import edu.bu.mbta.predictionTable.createTable;
import edu.bu.mbta.predictionTable.updatePrediction;

public class Orange extends Thread{
	
	AmazonDynamoDBClient dynamoDB;
	public Orange(AmazonDynamoDBClient DB) {
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
		String tableName = "PredictionsByRoute_Orange";
		createTable.create(dynamoDB, tableName);
		while (true) {
			updatePrediction.update(dynamoDB, tableName, "Orange");
			Thread.sleep(1000L * 60L);
		}
	}
	

}
