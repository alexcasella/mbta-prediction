package com.mbta.entity;

import java.util.ArrayList;

public class AllAlertsEntity {

	private ArrayList<AlertEntity> alerts;

	private String timestamp;

	public ArrayList<AlertEntity> getAlerts() {
		return alerts;
	}

	public void setAlerts(ArrayList<AlertEntity> alerts) {
		this.alerts = alerts;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
