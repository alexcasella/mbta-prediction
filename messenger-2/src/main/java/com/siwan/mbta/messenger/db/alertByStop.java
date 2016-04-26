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


public class alertByStop {
	
	static AmazonDynamoDBClient dynamoDB;	
    public String query(String start){
    	
    	AWSCredentials credentials = null;
//        try {
//            credentials = new ProfileCredentialsProvider("Jessica").getCredentials();
//        } catch (Exception e) {
//            throw new AmazonClientException(
//                    "Cannot load the credentials from the credential profiles file. " +
//                    "Please make sure that your credentials file is at the correct " +
//                    "location (/Users/yangsiwan/.aws/credentials), and is in valid format.",
//                    e);
//        }
        dynamoDB = new AmazonDynamoDBClient(new InstanceProfileCredentialsProvider ());
//        dynamoDB = new AmazonDynamoDBClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        dynamoDB.setRegion(usWest2);
        DynamoDB dynamoDB1 = new DynamoDB(dynamoDB);

	   Table table = dynamoDB1.getTable("AlertHeaderByStop");
   	   HashMap<String, String> nameMap = new HashMap<String, String>();
       nameMap.put("#nm", "stop_id"); 
        
       HashMap<String, Object> valueMap = new HashMap<String, Object>();
       valueMap.put(":yyyy", start);
        
   		QuerySpec spec = new QuerySpec()
   			.withKeyConditionExpression("#nm = :yyyy")
   			.withNameMap(nameMap)
   			.withValueMap(valueMap);
   	
   		ItemCollection<QueryOutcome> items = table.query(spec);
       
       System.out.println("\nfindRepliesForAThread results:");
       Iterator<Item> iterator = items.iterator();
       StringBuilder str = new StringBuilder();
       while (iterator.hasNext()) {
       		str.append(iterator.next());
       }
      
       String result = str.toString();
       if(result.equals("") || result.equals(null)) {
    	   return "Currently there is no alert header for this stop.";
       }
       
       String results = result;
       int m = results.indexOf("[");
       int n = results.lastIndexOf("]");
       results = results.substring(m+1, n);
       
       int count = -1;       
       int index1 = result.indexOf("[");
       int index2 = result.indexOf("]");      
       result = result.substring(index1+1, index2);
       
       String[] arr = new String[5];
       while(index1!=-1 && index2!=-1) {
    	   count++;
    	   arr[count] = result;
           index1 = result.indexOf("[");
           index2 = result.indexOf("]");
       }
       
       count++;
       
       if(arr.length == 1) {
    	   return arr[0];
       } else {
    	   //return " The next coming train has " + count + " alerts: " + results ;
    	   return results ;
       }
       
       
    }    
}
