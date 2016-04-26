package edu.bu.mbta.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

import edu.bu.mbta.api.AlertById;
import edu.bu.mbta.entity.*;

public class Main {

	public static final String mbtaBaseURI = "http://realtime.mbta.com/developer/api/v2/";
	public static final String apiKey = "?api_key=TGmTHy9PgES2eVZlZ76Mjg&";
	public static final String format = "&format=json";


}
