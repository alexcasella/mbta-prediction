package com.siwan.mbta.messenger.db;

import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;

public class predictionByRoute {
	
	static AmazonDynamoDBClient dynamoDB;	
	
	//The query function to obtain the predictions of the stop from a route
    public String query(String line, String start){ 
    	
    	//get credential to access DynamoDB
    	AWSCredentials credentials = null;
//    	try {
//    	credentials = new ProfileCredentialsProvider("default").getCredentials();
//    	} catch (Exception e) {
//    	throw new AmazonClientException(
//    	      "Cannot load the credentials from the credential profiles file. " +
//    	      "Please make sure that your credentials file is at the correct " +
//    	      "location (/Users/yangsiwan/.aws/credentials), and is in valid format.",
//    	      e);
//    	}
//    	dynamoDB = new AmazonDynamoDBClient(credentials);
    	dynamoDB = new AmazonDynamoDBClient(new InstanceProfileCredentialsProvider ());        
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        dynamoDB.setRegion(usWest2);
        DynamoDB dynamoDB1 = new DynamoDB(dynamoDB);
        
        //Get the table we want from database
        Table table = dynamoDB1.getTable("PredictionsByRoute_"+line);
        
        //Define the element we want to query from the table
   	    HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#nm", "stop_id"); 
        
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":yyyy", start);
        
   		QuerySpec spec = new QuerySpec()
   			.withKeyConditionExpression("#nm = :yyyy")
   			.withNameMap(nameMap)
   			.withValueMap(valueMap);
   		
   		ItemCollection<QueryOutcome> items = table.query(spec);
       
   		//Get the query result
        System.out.println("\nfindRepliesForAThread results:");
        Iterator<Item> iterator = items.iterator();
        StringBuilder str = new StringBuilder();
        while (iterator.hasNext()) {
       		str.append(iterator.next());
        }
       
        String result = str.toString();
        
        // Extract the information we want
        if(result.equals("")||result.equals(null)) {
        	return "currently there is no prediction for the station, please try later or go to service 3.";
        }
        
		result= result.trim();
        String builttime = result.substring(result.lastIndexOf("=")+1, result.lastIndexOf("}")-2);
        builttime = builttime.trim();
        System.out.println("built time:  "+builttime);
        int index1 = result.indexOf("[");
        int index2 = result.indexOf("]");
        System.out.println(result);
        result = result.substring(index1+1, index2);
        result = result.trim();
 
        String[] resultarr = result.split(",");
        
        long milliseconds = System.currentTimeMillis();
        System.out.println("current time:"+milliseconds);
        long value = 0;
        if(milliseconds > Long.parseLong(builttime)) {
        	long gap =  (milliseconds - Long.parseLong(builttime))/1000;
        	System.out.println(gap+"");
        	for(int i = 0; i < resultarr.length; i++) {
        		String tmp = resultarr[i].trim();
        		if(Long.parseLong(tmp) > gap) {
        			value = Long.parseLong(tmp) - gap;
        			if(value <= 60) {
	        	        return "The next coming " + line +
	        	        		" line train will be arriving in " + value + " seconds. ";
        			} else {
        				if(value%60 == 0) {
		        	        return "The next coming " + line +
		        	        		" line train will be arriving in " + value/60 + " minutes. ";
        				} else {
        					return "The next coming " + line +
		        	        		" line train will be arriving in " + value/60 + " minutes " + value%60 + " seconds. ";
        				}
        			}
        		}
        	}        	
        }

        return "Currently there is no records for this station, please try later. ";
    }    
}


