package com.mbta.entity;

import java.util.ArrayList;

public class RouteEntity {

	private String route_id;

	private String route_name;

	private String route_hide;

	private ArrayList<DirectionEntity> direction;

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

	public String getRoute_hide() {
		return route_hide;
	}

	public void setRoute_hide(String route_hide) {
		this.route_hide = route_hide;
	}

	public ArrayList<DirectionEntity> getDirection() {
		return direction;
	}

	public void setDirection(ArrayList<DirectionEntity> direction) {
		this.direction = direction;
	}

}
