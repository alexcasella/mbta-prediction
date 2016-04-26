package edu.bu.mbta.predictionTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class updateAllAlerts {
	public static void updateAlerts(AmazonDynamoDBClient dynamoDB,String tableName) throws Exception {
        ArrayList<String> AllIds = fetchItems("All-Stops-Table", dynamoDB);
        
        while(AllIds.size() > 0) {
        	int index = AllIds.size() - 1;
        	String id = AllIds.remove(index);
            
        	ItemCollection<QueryOutcome> items = queryAlertHeader(dynamoDB, tableName, id);

	        System.out.println("\nfindRepliesForAThread results:");
	        Iterator<Item> iterator = items.iterator();
	        StringBuilder str = new StringBuilder();
	        while (iterator.hasNext()) {
	        	str.append(iterator.next());
	        }
	        String string = str.toString();
	      	System.out.println("alertheader: "+string);
	 
	   		int index1 = string.lastIndexOf("=");
    		int index2 = string.lastIndexOf("}");
	       	if(index1 == -1 && index2 == -1) continue;
	       	string = string.substring(index1, index2);
	       	System.out.println(string);
	       	index2 = string.lastIndexOf("}");
	       	System.out.println("index2: "+ index2);
	       	string = string.substring(1, index2);
	       
	       	System.out.println("alertid: "+string);
	       	System.out.println("comma index:"+ string.indexOf(","));
	       		
	        if(string.indexOf(",") != -1) {
	       		String[] array = string.split(",");
	       		StringBuilder result = new StringBuilder();
	       		StringBuilder alertids = new StringBuilder();
	       		for(int i = 0; i < array.length; i++) {
	       			DynamoDB dynamoDB2 = new DynamoDB(dynamoDB);
	       	 	   	Table table2 = dynamoDB2.getTable("All-Alerts-Table");
	       	 	   	
	       	 	   	HashMap<String, String> nameMap2 = new HashMap<String, String>();
	       	 	   	nameMap2.put("#nm", "alert_id"); 
	       	        
	       	 	   	HashMap<String, Object> valueMap2 = new HashMap<String, Object>();
	       	 	   	valueMap2.put(":yyyy", array[i]);
	       	        
	       	   		QuerySpec spec2 = new QuerySpec()
	       	   			.withKeyConditionExpression("#nm = :yyyy")
	       	   			.withNameMap(nameMap2)
	       	   			.withValueMap(valueMap2);
	       	   	
	       	   		ItemCollection<QueryOutcome> items2 = table2.query(spec2);

		            System.out.println("\nfindRepliesForAThread results:");
		            Iterator<Item> iterator2 = items2.iterator();
		            StringBuilder str2 = new StringBuilder();
		            while (iterator2.hasNext()) {
		            	str2.append(iterator2.next());
		            }
		            String string2 = str.toString();
		            System.out.println("alert detail: " + string2);
		            alertids.append(array[i]);
		            result.append(string2);
	       		  }       			
	       			if( result.toString() == "" ||result == null||string==null) continue;
		            Map<String, AttributeValue> item2 = newItem(id, alertids.toString(), result.toString());
		            PutItemRequest putItemRequest2 = new PutItemRequest(tableName, item2);
		            PutItemResult putItemResult2 = dynamoDB.putItem(putItemRequest2);
		            System.out.println("Result: " + putItemResult2.toString());
	       		} else {
	       			System.out.println("Here!");
	       			
	       			ItemCollection<QueryOutcome> items2 = queryAllAlert(dynamoDB, tableName, string);

	       	   		if(items2.equals(null)) continue;

		            System.out.println("\nfindRepliesForAThread results:");
		            Iterator<Item> iterator2 = items2.iterator();
		            StringBuilder str2 = new StringBuilder();
		            while (iterator2.hasNext()) {
		              	str2.append(iterator2.next());
		            }
		            String string2 = str2.toString();
		            
		            System.out.println("alert detail: " + string2);
		            if(string2.equals("N/A") || string2.equals("")) continue;
			            Map<String, AttributeValue> item2 = newItem(id, string, string2);
			            PutItemRequest putItemRequest2 = new PutItemRequest(tableName, item2);
			            PutItemResult putItemResult2 = dynamoDB.putItem(putItemRequest2);
			            System.out.println("Result: " + putItemResult2.toString());
	       		}
     		
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
	
	
	private static Map<String, AttributeValue> newItem(String stop_id, String alertid, String information) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("stop_id", new AttributeValue(stop_id));
        item.put("alert_id", new AttributeValue(alertid));
        item.put("information", new AttributeValue(information));
  

        return item;
    }
	
	private static ItemCollection<QueryOutcome> queryAlertHeader(AmazonDynamoDBClient dynamoDB, String tableName, String stopid) {
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
	
	private static ItemCollection<QueryOutcome> queryAllAlert(AmazonDynamoDBClient dynamoDB, String tableName, String string) {
			DynamoDB dynamoDB2 = new DynamoDB(dynamoDB);
	 	   	Table table2 = dynamoDB2.getTable("All-Alerts-Table");
	 	   	
	 	   	HashMap<String, String> nameMap2 = new HashMap<String, String>();
	 	   	nameMap2.put("#nm", "alert_id"); 
	        
	 	   	HashMap<String, Object> valueMap2 = new HashMap<String, Object>();
	 	   	valueMap2.put(":yyyy", string);
	        
	   		QuerySpec spec2 = new QuerySpec()
	   			.withKeyConditionExpression("#nm = :yyyy")
	   			.withNameMap(nameMap2)
	   			.withValueMap(valueMap2);
	   	
	   		ItemCollection<QueryOutcome> items2 = table2.query(spec2);
	   		return items2;
	}
}
