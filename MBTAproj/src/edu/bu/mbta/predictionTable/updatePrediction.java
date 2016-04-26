package edu.bu.mbta.predictionTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import edu.bu.mbta.api.PredictionsByRoute;
import edu.bu.mbta.entity.DirectionEntity;
import edu.bu.mbta.entity.PredictionsByRouteEntity;
import edu.bu.mbta.entity.StopEntity;
import edu.bu.mbta.entity.TripEntity;

public class updatePrediction {
	public static void update(AmazonDynamoDBClient dynamoDB,String tableName2, String str) throws Exception {
		
	    PredictionsByRouteEntity prediction = PredictionsByRoute.getPredictionByRoute(str);
	    HashMap<String, TreeSet<String>> map = new HashMap<>();  
	    try {
	    ArrayList<DirectionEntity> direction = prediction.getDirection();
	    for(DirectionEntity dir : direction) {
	    	String direction_id = dir.getDirection_id();
	    	if(direction_id == null) continue;
	        ArrayList<TripEntity> trip = dir.getTrip();
	        for(TripEntity t : trip) {
	        	String trip_id = t.getTrip_id();
	        	if(trip_id == null) continue;
	        	ArrayList<StopEntity> stop = t.getStop();
	        	for(StopEntity st : stop) {
	        		String stop_id = st.getStop_id();
	        		if(stop_id == null || stop_id.equals("N/A")) continue;
	        		
	        		String pre_away = "";
	        		if(st.getPre_away() == null) {
	        			pre_away="N/A";
	        		} else {
	        			pre_away=st.getPre_away();
	        		}
	        		
	        		if(map.containsKey(stop_id)) {
	        			map.get(stop_id).add(pre_away);
	        		} else {
	        			TreeSet<String> value = new TreeSet<>(new Comparator<String>() {
	        	            public int compare(String s1, String s2) {
	    	                return Integer.compare(Integer.valueOf(s1), Integer.valueOf(s2));
	        	            }
	        			});
	        			value.add(pre_away);
	        			map.put(stop_id, value);
	        		}
	        		
	        	}
	        }
	    }
	    
    	} catch(NullPointerException e) {
			ArrayList<String> ids = fetchItems(tableName2, dynamoDB);
	    	for(String id : ids) {
	    		TreeSet<String> value = new TreeSet<>();
	    		value.add("null");
	    		map.put(id,value);
	    	}
	    }
	    
	    Set<String> set = map.keySet();
	    for(String id: set) {
	    	long milliseconds = System.currentTimeMillis();
	    	String yourmilliseconds = milliseconds + "";

	    	Map<String, AttributeValue> item = newItem(id, map.get(id).toString(), yourmilliseconds);
	        PutItemRequest putItemRequest = new PutItemRequest(tableName2, item);
	        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
	        System.out.println("Result: " + putItemResult.toString());
	    }    
 
	}
	
	private static Map<String, AttributeValue> newItem(String stop_id, String pre_away, String create_time) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("stop_id", new AttributeValue(stop_id));
        item.put("pre_away", new AttributeValue(pre_away));
        item.put("current_time", new AttributeValue(create_time));

        return item;
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
