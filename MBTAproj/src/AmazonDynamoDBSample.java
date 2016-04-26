/*
 * Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import edu.bu.mbta.alertByStop.AlertByStop;
import edu.bu.mbta.alertHeaderByStop.AlertHeaderByStop;
import edu.bu.mbta.predictionByDay.predictionByDay;
//import edu.bu.mbta.predictionByDay.predictionByDayBlue;
import edu.bu.mbta.predictionByDay.predictionByDayGreenB;
import edu.bu.mbta.predictionByDay.predictionByDayGreenC;
import edu.bu.mbta.predictionByDay.predictionByDayGreenD;
import edu.bu.mbta.predictionByDay.predictionByDayGreenE;
import edu.bu.mbta.predictionByDay.predictionByDayOrange;
import edu.bu.mbta.predictionByDay.predictionByDayRed;
import edu.bu.mbta.predictionByRoute.Blue;
import edu.bu.mbta.predictionByRoute.GreenB;
import edu.bu.mbta.predictionByRoute.GreenC;
import edu.bu.mbta.predictionByRoute.GreenD;
import edu.bu.mbta.predictionByRoute.GreenE;
import edu.bu.mbta.predictionByRoute.Orange;
import edu.bu.mbta.predictionByRoute.Red;
import edu.bu.mbta.predictionByStop.predictionByStop;
import edu.bu.mbta.predictionTable.predictionByDate;

/**
 * This sample demonstrates how to perform a few simple operations with the
 * Amazon DynamoDB service.
 */
public class AmazonDynamoDBSample {

    /*
     * Before running the code:
     *      Fill in your AWS access credentials in the provided credentials
     *      file template, and be sure to move the file to the default location
     *      (/Users/yangsiwan/.aws/credentials) where the sample code will load the
     *      credentials from.
     *      https://console.aws.amazon.com/iam/home?#security_credential
     *
     * WARNING:
     *      To avoid accidental leakage of your credentials, DO NOT keep
     *      the credentials file in your source directory.
     */

    static AmazonDynamoDBClient dynamoDB;

    /**
     * The only information needed to create a client are security credentials
     * consisting of the AWS Access Key ID and Secret Access Key. All other
     * configuration, such as the service endpoints, are performed
     * automatically. Client parameters, such as proxies, can be specified in an
     * optional ClientConfiguration object when constructing a client.
     *
     * @see com.amazonaws.auth.BasicAWSCredentials
     * @see com.amazonaws.auth.ProfilesConfigFile
     * @see com.amazonaws.ClientConfiguration
     */
    private static void init() throws Exception {
        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (/Users/yangsiwan/.aws/credentials).
         */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/yangsiwan/.aws/credentials), and is in valid format.",
                    e);
        }
        dynamoDB = new AmazonDynamoDBClient(credentials);
//        dynamoDB = new AmazonDynamoDBClient(new InstanceProfileCredentialsProvider ());
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        dynamoDB.setRegion(usWest2);
    }
    
    /*
     * Static variables for the query
     * */

    
    public static void main(String[] args) throws Exception {
        init();
            
/***********************AlertsByStop Table*********************************/ 
		Thread alerts = new Thread(new AlertByStop(dynamoDB));
		alerts.start();
		
		Thread alertHeaders = new Thread(new AlertHeaderByStop(dynamoDB));
		alertHeaders.start();

            
 /************************PredictionsByRoute Table********************************/
        
		Thread updateprebydate = new Thread(new predictionByDay(dynamoDB));
		updateprebydate.start();
        
//		Thread updateprebydateBlue = new Thread(new predictionByDayBlue(dynamoDB));
//		updateprebydateBlue.start();
		
		Thread updateprebydateRed = new Thread(new predictionByDayRed(dynamoDB));
		updateprebydateRed.start();
		
		Thread updateprebydateOrange = new Thread(new predictionByDayOrange(dynamoDB));
		updateprebydateOrange.start();
		
		Thread updateprebydateGreenB = new Thread(new predictionByDayGreenB(dynamoDB));
		updateprebydateGreenB.start();
		
		Thread updateprebydateGreenC = new Thread(new predictionByDayGreenC(dynamoDB));
		updateprebydateGreenC.start();
		
		Thread updateprebydateGreenD = new Thread(new predictionByDayGreenD(dynamoDB));
		updateprebydateGreenD.start();
		
		Thread updateprebydateGreenE = new Thread(new predictionByDayGreenE(dynamoDB));
		updateprebydateGreenE.start();
        
		Thread greenb = new Thread(new GreenB(dynamoDB));
		greenb.start();
		
		Thread greenc = new Thread(new GreenC(dynamoDB));
		greenc.start();
		
		Thread greend = new Thread(new GreenD(dynamoDB));
		greend.start();
		
		Thread greene = new Thread(new GreenE(dynamoDB));
		greene.start();
		
		Thread red = new Thread(new Red(dynamoDB));
		red.start();
		
		Thread orange = new Thread(new Orange(dynamoDB));
		orange.start();
		
		Thread blue = new Thread(new Blue(dynamoDB));
		blue.start();

                        
  /**********************************Prediction By Stop Table*******************************************/
		
//		Thread updateprebystop = new Thread(new predictionByStop(dynamoDB));
//		updateprebystop.start();
		
		
		
/*            String tableName3 = "PredictionsByStop_Green-B";
            // Create table if it does not exist yet
        if (Tables.doesTableExist(dynamoDB, tableName3)) {
            System.out.println("Table " + tableName3 + " is already ACTIVE");
        } else {
                // Create a table with a primary hash key named 'name', which holds a string
            CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName3)
                .withKeySchema(new KeySchemaElement().withAttributeName("name").withKeyType(KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition().withAttributeName("name").withAttributeType(ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
                TableDescription createdTableDescription = dynamoDB.createTable(createTableRequest).getTableDescription();
            System.out.println("Created Table: " + createdTableDescription);

                // Wait for it to become active
            System.out.println("Waiting for " + tableName3 + " to become ACTIVE...");
            Tables.awaitTableToBecomeActive(dynamoDB, tableName3);
        }
                
            DescribeTableRequest describeTableRequest3 = new DescribeTableRequest().withTableName(tableName3);
            TableDescription tableDescription3 = dynamoDB.describeTable(describeTableRequest3).getTable();
            System.out.println("Table Description: " + tableDescription3);*/

             

/**********************Get prediction by Stop id and store into table************************/
/*            ArrayList<String> AllIds2 = fetchItems("All-Stops-Table");

            while(AllIds2.size() > 0) {
            	int index = AllIds2.size() - 1;
            	String id = AllIds2.remove(index);
                
            	PredictionsByStopEntity prediction1 = PredictionsByStop.getPredictionByStop(id);
                ArrayList<ModeEntity> mode = prediction1.getMode();
                
                if(mode.size() == 0 || mode == null) continue;
                StringBuilder tripid = new StringBuilder();
                StringBuilder pre_away = new StringBuilder();
                for(ModeEntity m : mode) {
	                if(m.getRoute_type().equals("0") || m.getRoute_type().equals("1")) {
	                	ArrayList<RouteEntity> route = m.getRoute();
	                	
	                }
                }
	                
	                Map<String, AttributeValue> item2 = newItem1(id, tripid.toString(), pre_away.toString());
	                PutItemRequest putItemRequest2 = new PutItemRequest(tableName1, item2);
	                PutItemResult putItemResult2 = dynamoDB.putItem(putItemRequest2);
	                System.out.println("Result: " + putItemResult2);
                
            }*/
            
            
            // Scan items for movies with a year attribute greater than 1985
/*            HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
            Condition condition = new Condition()
                .withComparisonOperator(ComparisonOperator.GT.toString())
                .withAttributeValueList(new AttributeValue().withN("1985"));
            scanFilter.put("year", condition);
            ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            System.out.println("Result: " + scanResult);*/
            
//            DynamoDB dynamoDB1 = new DynamoDB(dynamoDB);
//            findAlertWithID(dynamoDB1);
//   
//        } catch (AmazonServiceException ase) {
//            System.out.println("Caught an AmazonServiceException, which means your request made it "
//                    + "to AWS, but was rejected with an error response for some reason.");
//            System.out.println("Error Message:    " + ase.getMessage());
//            System.out.println("HTTP Status Code: " + ase.getStatusCode());
//            System.out.println("AWS Error Code:   " + ase.getErrorCode());
//            System.out.println("Error Type:       " + ase.getErrorType());
//            System.out.println("Request ID:       " + ase.getRequestId());
//        } catch (AmazonClientException ace) {
//            System.out.println("Caught an AmazonClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with AWS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message: " + ace.getMessage());
//        }
    }


	
//	private static Map<String, AttributeValue> predictionItem(String stop_id, String direction_id, TreeSet<String> pre_away) {
//        Map<String, AttributeValue> item1 = new HashMap<String, AttributeValue>();
//        item1.put("name", new AttributeValue(stop_id));
////        item1.put("route_id", new AttributeValue(route_id));
//        item1.put("direction_id", new AttributeValue(direction_id));
//        item1.put("pre_away", new AttributeValue().withSS(pre_away));
////        item1.put("dep_time", new AttributeValue().withSS(dep_time));
//
//        return item1;
//    }
	

}