package edu.bu.mbta.entity;

public class StopEntity {
	
	private String stop_sequence;

	private String stop_id;

	private String stop_name;
	
	private String sch_arr_dt;

	private String sch_dep_dt;
	
	private String pre_dt;
	
	private String pre_away;

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
	
	public String getStop_sequence() {
		return stop_sequence;
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

	public void setStop_sequence(String stop_sequence) {
		this.stop_sequence = stop_sequence;
	}
	
	
	

}
