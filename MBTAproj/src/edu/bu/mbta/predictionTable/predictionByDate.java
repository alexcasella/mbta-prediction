package edu.bu.mbta.predictionTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import edu.bu.mbta.api.PredictionsByRoute;
import edu.bu.mbta.entity.DirectionEntity;
import edu.bu.mbta.entity.PredictionsByRouteEntity;
import edu.bu.mbta.entity.StopEntity;
import edu.bu.mbta.entity.TripEntity;

public class predictionByDate {

	public static void update(AmazonDynamoDBClient dynamoDB,String tableName2, String str) throws Exception {
		
	    PredictionsByRouteEntity prediction = PredictionsByRoute.getPredictionByRoute(str);
	    HashMap<String, TreeSet<String>> map = new HashMap<>();
	    ArrayList<DirectionEntity> direction = new ArrayList<DirectionEntity>();
	    try {
		    direction = prediction.getDirection();	      
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
				ArrayList<String> ids = fetchItems("All-Stops-Table", dynamoDB);
		    	for(String id : ids) {
		    		TreeSet<String> value = new TreeSet<>();
		    		value.add("null");
		    		map.put(id,value);
		    	}
		    }
	    
		    Set<String> set = new HashSet<String>();
		    set = map.keySet();
		   	for(String id: set) {
			   	StringBuilder sb = new StringBuilder();
			   	ItemCollection<QueryOutcome> items = queryStopPrediction(dynamoDB, tableName2, id);
				StringBuilder sbb = new StringBuilder();				
				if(!items.equals(null)) {					
				    Iterator<Item> iterator = items.iterator();	    
				    while (iterator.hasNext()) {			        
				    	sbb.append(iterator.next());
					}
					String tmp = sbb.toString();
					System.out.println("Origine"+tmp);					    
					if(!tmp.equals("") && !tmp.equals(null)) {
						tmp = tmp.substring(tmp.indexOf(","),tmp.lastIndexOf("}"));
						tmp = tmp.substring(tmp.indexOf("=")+1,tmp.lastIndexOf("}"));
					    sb = sb.append(tmp);
					    System.out.println("origine parse"+sb.toString());
				    }
				}
			    sb.append(map.get(id).toString());
			    System.out.println("new item"+sb.toString());
			    Map<String, AttributeValue> item = newItem(id, sb.toString());		    	
			    PutItemRequest putItemRequest = new PutItemRequest(tableName2, item);
			    PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
			    System.out.println("Result: " + putItemResult.toString());
		   	}

	    	
	}    
 
	
	private static Map<String, AttributeValue> newItem(String stop_id, String pre_away) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("stop_id", new AttributeValue(stop_id));
        item.put("pre_away", new AttributeValue(pre_away));

        return item;
    }
	
	private static ItemCollection<QueryOutcome> queryStopPrediction(AmazonDynamoDBClient dynamoDB, String tableName, String stopid) {
    	DynamoDB dynamoDB1 = new DynamoDB(dynamoDB);
 	   	Table table = dynamoDB1.getTable(tableName);
 	   	
 	   	HashMap<String, String> nameMap = new HashMap<String, String>();
 	   	nameMap.put("#nm", "stop_id"); 
        
 	   	HashMap<String, Object> valueMap = new HashMap<String, Object>();
 	   	valueMap.put(":yyyy", stopid);
        
   		QuerySpec spec = new QuerySpec()
   			.withKeyConditionExpression("#nm = :yyyy")
   			.withNameMap(nameMap)
   			.withValueMap(valueMap);
   	
   		ItemCollection<QueryOutcome> items = table.query(spec);
   		return items;
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
