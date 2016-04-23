package com.mbta.dbmapper;

import java.util.Map;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "All-Stops-Table")
public class AllStopsMapper {

	private String stop_id;

	private String stop_name;

	private String direction_id;

	private Map<String, String> routes;

	private String last_modified;

	@DynamoDBHashKey(attributeName = "stop_id")
	public String getStop_id() {
		return stop_id;
	}

	public void setStop_id(String stop_id) {
		this.stop_id = stop_id;
	}

	@DynamoDBAttribute(attributeName = "stop_name")
	public String getStop_name() {
		return stop_name;
	}

	public void setStop_name(String stop_name) {
		this.stop_name = stop_name;
	}

	@DynamoDBRangeKey(attributeName = "direction_id")
	public String getDirection_id() {
		return direction_id;
	}

	public void setDirection_id(String direction_id) {
		this.direction_id = direction_id;
	}
	
	@DynamoDBAttribute(attributeName = "routes")
	public Map<String, String> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<String, String> routes) {
		this.routes = routes;
	}

	@DynamoDBAttribute(attributeName = "last_modified")
	public String getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}

}
