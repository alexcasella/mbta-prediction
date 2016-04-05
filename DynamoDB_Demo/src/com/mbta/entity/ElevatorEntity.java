package com.mbta.entity;

import java.util.ArrayList;

import com.mbta.entity.StopEntity;

public class ElevatorEntity {
	private String elev_id;

	private String elev_name;

	private String elev_type;

	private ArrayList<StopEntity> stops;

	public String getElev_id() {
		return elev_id;
	}

	public void setElev_id(String elev_id) {
		this.elev_id = elev_id;
	}

	public String getElev_name() {
		return elev_name;
	}

	public void setElev_name(String elev_name) {
		this.elev_name = elev_name;
	}

	public String getElev_type() {
		return elev_type;
	}

	public void setElev_type(String elev_type) {
		this.elev_type = elev_type;
	}

	public ArrayList<StopEntity> getStops() {
		return stops;
	}

	public void setStops(ArrayList<StopEntity> stops) {
		this.stops = stops;
	}

}
