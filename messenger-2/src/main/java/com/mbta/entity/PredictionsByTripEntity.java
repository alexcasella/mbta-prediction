package com.mbta.entity;

import java.util.ArrayList;

public class PredictionsByTripEntity {

	private String route_id;

	private String route_name;

	private String route_type;

	private String mode_name;

	private String trip_id;

	private String trip_name;

	private String trip_headsign;

	private String direction_id;

	private String direction_name;

	private VehicleEntity vehicle;

	private ArrayList<StopEntity> stop;

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

	public void setRoute_type(String route_type) {
		this.route_type = route_type;
	}

	public String getMode_name() {
		return mode_name;
	}

	public void setMode_name(String mode_name) {
		this.mode_name = mode_name;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}

	public String getTrip_name() {
		return trip_name;
	}

	public void setTrip_name(String trip_name) {
		this.trip_name = trip_name;
	}

	public String getTrip_headsign() {
		return trip_headsign;
	}

	public void setTrip_headsign(String trip_headsign) {
		this.trip_headsign = trip_headsign;
	}

	public String getDirection_id() {
		return direction_id;
	}

	public void setDirection_id(String direction_id) {
		this.direction_id = direction_id;
	}

	public String getDirection_name() {
		return direction_name;
	}

	public void setDirection_name(String direction_name) {
		this.direction_name = direction_name;
	}

	public VehicleEntity getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleEntity vehicle) {
		this.vehicle = vehicle;
	}

	public ArrayList<StopEntity> getStop() {
		return stop;
	}

	public void setStop(ArrayList<StopEntity> stop) {
		this.stop = stop;
	}

}
