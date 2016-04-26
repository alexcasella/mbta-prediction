package com.mbta.entity;

import java.util.ArrayList;

public class StopsByRouteEntity {

	private String route_id;

	private String route_name;

	private ArrayList<DirectionEntity> direction;

	public String getRoute_id() {
		return route_id;
	}

	public String getRoute_name() {
		return route_name;
	}

	public ArrayList<DirectionEntity> getDirection() {
		return direction;
	}

	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	public void setRoute_name(String route_name) {
		this.route_name = route_name;
	}

	public void setDirection(ArrayList<DirectionEntity> direction) {
		this.direction = direction;
	}
}
