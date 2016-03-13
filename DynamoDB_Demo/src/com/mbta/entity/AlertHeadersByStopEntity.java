package com.mbta.entity;

import java.util.ArrayList;

public class AlertHeadersByStopEntity {

	private String stop_id;

	private String stop_name;

	ArrayList<AlertEntity> alert_headers;

	public String getStop_id() {
		return stop_id;
	}

	public void setStop_id(String stop_id) {
		this.stop_id = stop_id;
	}

	public String getStop_name() {
		return stop_name;
	}

	public void setStop_name(String stop_name) {
		this.stop_name = stop_name;
	}

	public ArrayList<AlertEntity> getAlert_headers() {
		return alert_headers;
	}

	public void setAlert_headers(ArrayList<AlertEntity> alert_headers) {
		this.alert_headers = alert_headers;
	}

}
