package com.mbta.entity;

public class StopEntity {

	private String stop_order;

	private String stop_id;

	private String stop_name;

	private String parent_station;

	private String parent_station_name;

	private String stop_lat;

	private String stop_lon;

	private String sch_arr_dt;

	private String sch_dep_dt;

	private String pre_dt;

	private String pre_away;

	public String getStop_order() {
		return stop_order;
	}

	public void setStop_order(String stop_order) {
		this.stop_order = stop_order;
	}

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

	public String getSch_arr_dt() {
		return sch_arr_dt;
	}

	public void setSch_arr_dt(String sch_arr_dt) {
		this.sch_arr_dt = sch_arr_dt;
	}

	public String getSch_dep_dt() {
		return sch_dep_dt;
	}

	public void setSch_dep_dt(String sch_dep_dt) {
		this.sch_dep_dt = sch_dep_dt;
	}

	public String getPre_dt() {
		return pre_dt;
	}

	public void setPre_dt(String pre_dt) {
		this.pre_dt = pre_dt;
	}

	public String getPre_away() {
		return pre_away;
	}

	public void setPre_away(String pre_away) {
		this.pre_away = pre_away;
	}

	public String getStop_lat() {
		return stop_lat;
	}

	public void setStop_lat(String stop_lat) {
		this.stop_lat = stop_lat;
	}

	public String getStop_lon() {
		return stop_lon;
	}

	public void setStop_lon(String stop_lon) {
		this.stop_lon = stop_lon;
	}

}
