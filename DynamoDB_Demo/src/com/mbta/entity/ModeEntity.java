package com.mbta.entity;

import java.util.ArrayList;

public class ModeEntity {

	private String route_type;

	private String mode_name;

	private ArrayList<RouteEntity> route;

	public String getRoute_type() {
		return route_type;
	}

	public void setRoute_type(String route_type) {
		this.route_type = route_type;
	}

	public String getMode_name() {
		return mode_name;
	}

	public void setMode_name(String mode_name) {
		this.mode_name = mode_name;
	}

	public ArrayList<RouteEntity> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<RouteEntity> route) {
		this.route = route;
	}

}
