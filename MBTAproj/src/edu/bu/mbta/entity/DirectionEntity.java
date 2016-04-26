package edu.bu.mbta.entity;

import java.util.ArrayList;
import edu.bu.mbta.entity.StopEntity;
import edu.bu.mbta.entity.TripEntity;

public class DirectionEntity {

	private String direction_id;

	private String direction_name;

	private ArrayList<StopEntity> stop;

	private ArrayList<TripEntity> trip;

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

	public ArrayList<StopEntity> getStop() {
		return stop;
	}

	public void setStop(ArrayList<StopEntity> stop) {
		this.stop = stop;
	}

	public ArrayList<TripEntity> getTrip() {
		return trip;
	}

	public void setTrip(ArrayList<TripEntity> trip) {
		this.trip = trip;
	}

}
