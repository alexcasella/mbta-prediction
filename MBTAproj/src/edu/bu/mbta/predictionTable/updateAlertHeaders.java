package edu.bu.mbta.predictionTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import edu.bu.mbta.api.AlertHeadersByStop;
import edu.bu.mbta.entity.AlertEntity;
import edu.bu.mbta.entity.AlertHeadersByStopEntity;

public class updateAlertHeaders {
	public static void updateAlertHeader(AmazonDynamoDBClient dynamoDB,String tableName) throws Exception {
        ArrayList<String> AllIds = fetchItems("All-Stops-Table", dynamoDB);

        while(AllIds.size() > 0) {
        	int index = AllIds.size() - 1;
        	String id = AllIds.remove(index);
            
            AlertHeadersByStopEntity alertHeader3 = AlertHeadersByStop.getAlertHeadersByStop(id);
            ArrayList<AlertEntity> alert3 = alertHeader3.getAlert_headers();
            if(alert3.size() == 0 || alert3 == null) continue;
            
            StringBuilder alertid = new StringBuilder();
            StringBuilder header = new StringBuilder();

            for(int i = 0; i < alert3.size(); i++) {
            	if(i != alert3.size() - 1) {
            		alertid.append(alert3.get(i).getAlert_id()).append(";");
            		header.append(alert3.get(i).getHeader_text()).append(";");
            	} else {
            		alertid.append(alert3.get(i).getAlert_id());
            		header.append(alert3.get(i).getHeader_text());
            	}
            }
            
            Map<String, AttributeValue> item2 = newItem(id, alertid.toString(), header.toString());
            PutItemRequest putItemRequest2 = new PutItemRequest(tableName, item2);
            PutItemResult putItemResult2 = dynamoDB.putItem(putItemRequest2);
            System.out.println("Result: " + putItemResult2);
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
	
	
	private static Map<String, AttributeValue> newItem(String stop_id, String alertid, String short_header_text) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("stop_id", new AttributeValue(stop_id));
        item.put("alert_id", new AttributeValue(alertid));
        item.put("short_header_text", new AttributeValue().withSS(short_header_text));

        return item;
    }
}
