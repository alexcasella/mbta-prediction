package edu.bu.mbta.entity;

public class StopEntity {

	private String stop_id;

	private String stop_name;

	private String parent_station;

	private String parent_station_name;

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

	public String getParent_station() {
		return parent_station;
	}

	public void setParent_station(String parent_station) {
		this.parent_station = parent_station;
	}

	public String getParent_station_name() {
		return parent_station_name;
	}

	public void setParent_station_name(String parent_station_name) {
		this.parent_station_name = parent_station_name;
	}

}
