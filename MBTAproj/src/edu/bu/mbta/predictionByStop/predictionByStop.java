package edu.bu.mbta.predictionByStop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import edu.bu.mbta.predictionTable.createTable;
import edu.bu.mbta.predictionTable.updatePredictionByStop;

public class predictionByStop extends Thread{
	
	AmazonDynamoDBClient dynamoDB;
	public predictionByStop(AmazonDynamoDBClient DB) {
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
		
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(yourmilliseconds);
		String date = sdf.format(resultdate);
		String hour = date.substring(date.lastIndexOf(" ")+1, date.indexOf(":"));
		date = date.substring(0, date.lastIndexOf(" "));
		String month = date.substring(0,date.lastIndexOf(" "));
		String day = date.substring(date.lastIndexOf(" ")+1, date.lastIndexOf(","));
		String year = date.substring(date.lastIndexOf(",")+1);
		
		String tableName = "PredictionsByStop_"+month + "_"+ day +"_"+year;		
		createTable.create(dynamoDB, tableName);
		while (true) {
			ArrayList<String> AllIds = fetchItems("All-Stops-Table", dynamoDB);
			while(AllIds.size() > 0) {
	        	int index = AllIds.size() - 1;
	        	String stopid = AllIds.remove(index);
	        	updatePredictionByStop.update(dynamoDB, tableName, stopid);
			}
			Thread.sleep(1000L * 60L* 60L);
		}
	}

	
	private static ArrayList<String> fetchItems(String tableName, AmazonDynamoDBClient dynamoDB) {	     
	    
		ArrayList<String> ids = new ArrayList<String>();	 
	    ScanResult result = null;
	 
	    do{
	        ScanRequest req = new ScanRequest();
	        req.setTableName(tableName);
	 
	        if(result != null){
	            req.setExclusiveStartKey(result.getLastEvaluatedKey());
	        }
	         
	        result = dynamoDB.scan(req);
	 
	        List<Map<String, AttributeValue>> rows = result.getItems();
	 
	        for(Map<String, AttributeValue> map : rows){
	            try{
	                AttributeValue v = map.get("stop_id");
	                String id = v.getS();
	                ids.add(id);
	            } catch (NumberFormatException e){
	                System.out.println(e.getMessage());
	            }
	        }
	    } while(result.getLastEvaluatedKey() != null);
	     
	    System.out.println("Result size: " + ids.size());
	 
	    return ids;
	}
}

