package com.siwan.mbta.messenger.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.ws.rs.PathParam;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

public class predictionByStop {
	
	static AmazonDynamoDBClient dynamoDB;	
	
	//The query function to obtain the predictions of the stop from a route
    public String query(String line, String start, String time){ 
    	
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
        
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(yourmilliseconds);
		String date = sdf.format(resultdate);
		System.out.println(date);
		date = date.substring(0, date.indexOf(":"));
		String month = date.substring(0,date.indexOf(" "));	
		date = date.substring(date.indexOf(" ")+1);
		String day = date.substring(0, date.indexOf(","));
		String year = date.substring(date.lastIndexOf(",")+1, date.lastIndexOf(" "));
		String hour = date.substring(date.lastIndexOf(" ")+1);
		
//		int requirehour = Integer.parseInt(time);
		int dayhour = Integer.parseInt(hour);
		int dayvalue = Integer.parseInt(day);
		int getday = 0;
		if(dayvalue == 1) {
			if(month.equals("Feb") || month.equals("Apr") || month.equals("Jun") || month.equals("Sep") || month.equals("Nov")) {
				getday = 30;
			} else {
				getday = 31;
			}
		} else {
			getday = dayvalue - 1;
		}
				
        //Get the table we want from database
        Table table = dynamoDB1.getTable("PredictionsByStop_"+line+"_"+month+"."+24+"."+year);
        
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
        	return "No prediction records for current station, please try later";
        }
        
		result= result.trim();
//		String extract = result.trim();
		int t = 0;
		String d = "";
		String actualtime = "";
		if(time.equals("030")) {
			t = 0;
			actualtime = "0:30";
		} else {
			if(time.length() == 1 || time.length() == 2) {
				 t = Integer.parseInt(time);
				 actualtime = time;
			} else {
				if(time.length() == 3) {				
					d = time.substring(0,1) + ".5";
					t = (int)Double.parseDouble(d)*2 - 1;
					actualtime = time.substring(0,1)+":30";
				} else if(time.length() == 4) {				
					d = time.substring(0,2) + ".5";
					t = (int)Double.parseDouble(d)*2 - 1;
					actualtime = time.substring(0,2)+":30";
				}
			}
		}
        for(int i = 0; i <=  t; i++) {
	        int index1 = result.indexOf("[");	        
	        result = result.substring(index1+1);
        }
        System.out.println(result);
        int index2 = result.indexOf("]");
        result = result.substring(0, index2);
        result = result.trim();
 
        String[] resultarr = result.split(",");        
//        long milliseconds = System.currentTimeMillis();
//        System.out.println("current time:"+milliseconds);
        int tt = 0;
        int tail = 0;
        if(time.length() == 3) {
        	tt = Integer.parseInt(actualtime.substring(0,1));
        	tail = 30;
        } else if(time.length() == 4) {
        	tt = Integer.parseInt(actualtime.substring(0,2));
        	tail = 30;
        } else {
        	tt = Integer.parseInt(actualtime);
        	tail = -1;
        }
        String ttt = "";
        String tttt = "";
        if(tt > 12) {
        	tt = tt - 12;
        	if(tail == -1) {
        		time = tt+"";
        		ttt = tt+" pm";
        	} else {
        		time = tt+"";
        		ttt = tt+":30"+" pm";
        	}
        	tttt="pm";
        } else {
        	if(tail == -1) {
        		time = tt+"";
        		ttt = tt+" am";
        	} else {
        		time = tt+"";
        		ttt = tt+":30"+" am";
        	}
        	tttt="am";
        }
        StringBuilder results = new StringBuilder();
        results.append("There will be "+ resultarr.length + " "+ line +" line trains arriving at: " );
        for(int i = 0; i < resultarr.length; i++) {
        	String tmp = resultarr[i].trim();
        	int value = Integer.parseInt(tmp);
        	if(value <= 60) {
        		if(tail == -1) {
        			results.append(time+tttt+", ");
        		} else {
        			results.append(ttt +", ");
        		}
			} else {
				if(tail == -1) {
					if(value/60 < 10) {
						results.append(time + ":0" + value/60 + tttt + ", ");
					} else {
						results.append(time + ":" + value/60 + tttt + ", ");
					}
				} else {
					int v = 30 + value/60;
					if(v <= 59) {
						if(v < 10) {
							results.append(time + ":0" + v + tttt + ", ");
						} else {
							results.append(time + ":" + v + tttt + ", ");
						}
					} else {
						int v1 = v/60;
						int v2 = v%60;
						time = (Integer.parseInt(time) + v1)+"";
						if(v2 < 10) {
							results.append(time + ":0" + v2 + tttt + ", ");
						} else {
							results.append(time + ":" + v2 + tttt + ", ");
						}
					}
						
				}
			}
		}        		        		         

        String r = results.toString();
        r = r.substring(0, r.lastIndexOf(",")) + ".";
        
        StringBuilder last = new StringBuilder();
        last = last.append(r.substring(0,r.indexOf(":") + 1));   
        r = r.substring(r.indexOf(":")+1, r.indexOf("."));   
        r = r.trim();     
        String[] compare = r.split(",");
        
        last.append(compare[0]+",");
        for(int i = 0; i < compare.length - 1; i++) {
        	if(!compare[i].trim().equals(compare[i+1].trim())) {
        		last.append(compare[i+1]+",");
        	}
        }        
        String l = last.toString();
        l = l.substring(0, l.lastIndexOf(",")) + ".";
        
        if(tt - 6 > 1) {
        	l = l + " For more precise prediction please check service 1 later.";
        }
        return l;
    }    
}
