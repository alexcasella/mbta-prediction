package com.mbta.dbmapper;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "All-Alerts-Table")
public class AllAlertsMapper {

	private String alert_id;

	private String effect_name;

	private String header_text;

	private String severity;

	private String service_effect_text;

	private Map<String, String> effect_periods;

	private String created_date;

	@DynamoDBHashKey(attributeName = "alert_id")
	public String getAlert_id() {
		return alert_id;
	}

	public void setAlert_id(String alert_id) {
		this.alert_id = alert_id;
	}

	@DynamoDBAttribute(attributeName = "effect_name")
	public String getEffect_name() {
		return effect_name;
	}

	public void setEffect_name(String effect_name) {
		this.effect_name = effect_name;
	}

	@DynamoDBAttribute(attributeName = "header_text")
	public String getHeader_text() {
		return header_text;
	}

	public void setHeader_text(String header_text) {
		this.header_text = header_text;
	}

	@DynamoDBAttribute(attributeName = "severity")
	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	@DynamoDBAttribute(attributeName = "service_effect_text")
	public String getService_effect_text() {
		return service_effect_text;
	}

	public void setService_effect_text(String service_effect_text) {
		this.service_effect_text = service_effect_text;
	}

	@DynamoDBAttribute(attributeName = "effect_periods")
	public Map<String, String> getEffect_periods() {
		return effect_periods;
	}

	public void setEffect_periods(Map<String, String> effect_periods) {
		this.effect_periods = effect_periods;
	}
	
	@DynamoDBAttribute(attributeName = "created_date")
	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

}
