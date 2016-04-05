package com.mbta.dbmapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Routes")
public class AllRoutesMapper {

	private String route_id;

	private String route_name;

	private String route_hide;

	private String route_type;

	private String mode_name;

	private String last_modified;

	@DynamoDBHashKey(attributeName = "route_id")
	public String getRoute_id() {
		return route_id;
	}

	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	@DynamoDBAttribute(attributeName = "route_name")
	public String getRoute_name() {
		return route_name;
	}

	public void setRoute_name(String route_name) {
		this.route_name = route_name;
	}

	public String getRoute_hide() {
		return route_hide;
	}

	public void setRoute_hide(String route_hide) {
		this.route_hide = route_hide;
	}

	@DynamoDBAttribute(attributeName = "route_type")
	public String getRoute_type() {
		return route_type;
	}

	public void setRoute_type(String route_type) {
		this.route_type = route_type;
	}

	@DynamoDBAttribute(attributeName = "mode_name")
	public String getMode_name() {
		return mode_name;
	}

	public void setMode_name(String mode_name) {
		this.mode_name = mode_name;
	}

	@DynamoDBAttribute(attributeName = "last_modified")
	public String getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}
}
