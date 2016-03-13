package com.mbta.entity;

import java.util.ArrayList;

public class PredictionsByStopEntity {

	private String stop_id;

	private String stop_name;

	private ArrayList<ModeEntity> mode;

	private ArrayList<AlertEntity> alert_headers;

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

	public ArrayList<ModeEntity> getMode() {
		return mode;
	}

	public void setMode(ArrayList<ModeEntity> mode) {
		this.mode = mode;
	}

	public ArrayList<AlertEntity> getAlert_headers() {
		return alert_headers;
	}

	public void setAlert_headers(ArrayList<AlertEntity> alert_headers) {
		this.alert_headers = alert_headers;
	}

}
