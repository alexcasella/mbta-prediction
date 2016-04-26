package edu.bu.mbta.predictionTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

import edu.bu.mbta.api.PredictionsByStop;
import edu.bu.mbta.entity.DirectionEntity;
import edu.bu.mbta.entity.ModeEntity;
import edu.bu.mbta.entity.PredictionsByStopEntity;
import edu.bu.mbta.entity.RouteEntity;
import edu.bu.mbta.entity.TripEntity;

public class updatePredictionByStop {
	public static void update(AmazonDynamoDBClient dynamoDB,String tableName2, String stopid) throws Exception {		
		ItemCollection<QueryOutcome> items = queryStopPrediction(dynamoDB, tableName2, stopid);
		StringBuilder sbb = new StringBuilder();	        
	    Iterator<Item> iterator = items.iterator();
	    while (iterator.hasNext()) {
	        sbb.append(iterator.next());
	    }
	    String tmp = sbb.toString();
	    StringBuilder sb = new StringBuilder();
	    if(!tmp.equals("") && !tmp.equals(null)) {
		    tmp = tmp.substring(tmp.indexOf(","),tmp.lastIndexOf("}"));
		    tmp = tmp.substring(tmp.indexOf("=")+1,tmp.lastIndexOf("}"));
		    sb = sb.append(tmp);
		    System.out.println(sb.toString());
	    }
	    String sbitem = "";	  
		TreeSet<String> value = new TreeSet<>(new Comparator<String>() {
            public int compare(String s1, String s2) {
            return Integer.compare(Integer.valueOf(s1), Integer.valueOf(s2));
            }
		});
		PredictionsByStopEntity prediction = PredictionsByStop.getPredictionByStop(stopid);
		try {
			ArrayList<ModeEntity> mode = prediction.getMode();
		    for(ModeEntity m : mode) {
				if(m.equals(null)) break;
				if(m.getRoute_type().equals("0")||m.getRoute_type().equals("1")) {
					ArrayList<RouteEntity> route= m.getRoute();
				    for(RouteEntity r : route) {
				    	ArrayList<DirectionEntity> direction = r.getDirection();
				    	for(DirectionEntity d : direction) {
				    		ArrayList<TripEntity> trip = d.getTrip();
				    		for(TripEntity t : trip) {
			        			value.add(t.getPre_away());				 
				    		}
				    	}
				    }
				}
			}
		    
		    sb.append(value.toString().substring(1));
		    System.out.println(sb.toString());
			sbitem = sb.toString();
			sbitem = sbitem.substring(0, sbitem.length()-1);		
			sbitem = sbitem + ";";
			System.out.println(sbitem);
		} catch(NullPointerException e) {
		    sb.append("null;");
		    sbitem = sb.toString();
		}
		    
	    Map<String, AttributeValue> item = newItem(stopid,sbitem);
	    PutItemRequest putItemRequest = new PutItemRequest(tableName2, item);
	    PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);  
	    System.out.println("Result: " + putItemResult.toString());  

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
}
