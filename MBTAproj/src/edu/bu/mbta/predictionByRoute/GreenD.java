package edu.bu.mbta.predictionByRoute;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import edu.bu.mbta.predictionTable.createTable;
import edu.bu.mbta.predictionTable.updatePrediction;

public class GreenD extends Thread{
	AmazonDynamoDBClient dynamoDB;
	public GreenD(AmazonDynamoDBClient DB) {
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
		String tableName = "PredictionsByRoute_GreenD";
		createTable.create(dynamoDB, tableName);
		while (true) {
			updatePrediction.update(dynamoDB, tableName, "Green-D");
			Thread.sleep(1000L * 60L);
		}
	}
}
