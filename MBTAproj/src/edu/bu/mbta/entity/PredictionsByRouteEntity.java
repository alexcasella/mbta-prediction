package edu.bu.mbta.entity;

import java.util.ArrayList;

public class PredictionsByRouteEntity {

	private String route_id;

	private String route_name;
	
	private String route_type;
	
	private String mode_name;

	private ArrayList<AlertEntity> alert_headers;
	
	private ArrayList<DirectionEntity> direction;	

	public ArrayList<DirectionEntity> getDirection() {
		return direction;
	}

	public String getRoute_id() {
		return route_id;
	}
	
	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	public String getRoute_name() {
		return route_name;
	}

	public void setRoute_name(String route_name) {
		this.route_name = route_name;
	}
	
	public String getRoute_type() {
		return route_type;
	}
	
	public String getMode_name() {
		return mode_name;
	}

	public ArrayList<AlertEntity> getAlert_headers() {
		return alert_headers;
	}

	public void setAlert_headers(ArrayList<AlertEntity> alert_headers) {
		this.alert_headers = alert_headers;
	}

}
