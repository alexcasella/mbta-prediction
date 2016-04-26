package com.siwan.mbta.messenger.db;

import java.util.ArrayList;
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


public class AlertDetailByStop {
	
	static AmazonDynamoDBClient dynamoDB;	
    public String query( String start){
    	
    	AWSCredentials credentials = null;
//        try {
//            credentials = new ProfileCredentialsProvider("default").getCredentials();
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

	   Table table = dynamoDB1.getTable("AlertsByStop");
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
       
       String parseString = str.toString();
       
       if(parseString.equals("") || parseString.equals(null)) {
    	   return "No further details at this time.";
       }
		parseString = parseString.trim();
		parseString = parseString.substring(1);
		parseString = parseString.substring(parseString.indexOf("{")+1);		
		parseString = parseString.substring(parseString.indexOf("{")+1);
       
       int index1 = parseString.indexOf(",");
       parseString = parseString.substring(index1+1);
       
       int index2 = parseString.indexOf("=");
       int index3 = parseString.indexOf(",");
       String effect_name = parseString.substring(index2+1, index3);  //get the effect name
       parseString = parseString.substring(index3);
       
      
       parseString = parseString.substring(parseString.indexOf("{"));
       int index5 = parseString.indexOf("{");
       
       int index6 = parseString.indexOf("}");
       ArrayList<String> effect_start = new ArrayList<String>();
       ArrayList<String> effect_end = new ArrayList<String>();
       String time_period = parseString.substring(index5+1, index6);//get effect end time
       if(time_period.indexOf(",") == -1) {
    	   String start1 = time_period.substring(0, time_period.indexOf("="));
    	   effect_start.add(toDate(start1));	    	   
    	   String end = time_period.substring(time_period.indexOf("=")+1);
		   if(end.equals("Unknown")) {
			   effect_end.add(end);
		   }else {
			   effect_end.add(toDate(end));
		   }
       }else {
    	   String[] time = time_period.split(",");
    	   for(String str1 : time) {
    		   str1 = str1.trim();
    		   String start1 = str1.substring(0, str1.indexOf("="));
    		   effect_start.add(toDate(start1));
    		   String end = str1.substring(str1.indexOf("=")+1);
    		   if(end.equals("Unknown")) {
    			   effect_end.add(end);
    		   }else {
    			   effect_end.add(toDate(end));
    		   }
    	   }
       }
       
       String[] a = new String[effect_start.size()];
       a = effect_start.toArray(a);
       
       String[] b = new String[effect_end.size()];
       b = effect_end.toArray(b);
       
       String start_effect = "";
       String end_effect = "";
       StringBuilder time = new StringBuilder();
       
       if(a.length == 1) {
    	   start_effect = a[0];
    	   end_effect = b[0];
       }else {
    	   time.append("This alert affects " + a.length + " time period, ");
	       for(int i = 0; i < a.length; i++) {
	    	   time.append("from "+ a[i] + " to " + b[i] + "; ");
	       }
       }
       
	   parseString = parseString.substring(index6);		       
	   parseString = parseString.substring(parseString.indexOf("=")+1);
	   parseString = parseString.substring(parseString.indexOf("=")+1);
	   String severity = parseString.substring(0,parseString.indexOf(","));
	       	       
	   String alert_datail = parseString.substring(parseString.indexOf("=")+1,parseString.lastIndexOf(","));
	   
	   String result = "";
	   if(a.length == 1) {
//		   result = alert_datail +" It starts from " + start_effect + " and will end at " + end_effect + "." + 
//				   " The condition of the alert is " + severity;
		   result = "The alert starts from " + start_effect + " and will end at " + end_effect + "." + 
				   " The condition of the alert is " + severity;
	   } else {
//		   result = alert_datail  + ", "+ time.toString() + " The condition of the alert is " + severity;
		   result = time.toString() + " The condition of the alert is " + severity;

	   }

       return result;       
      
    }
    
	public static String toDate(String epoch_time) {
		long epoch = Long.valueOf(epoch_time);
		String date = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss a")
				.format(new java.util.Date(epoch * 1000));

		return date;

	}
}
