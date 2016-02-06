package edu.bu.mbta.entity;

public class ElevatorEntity {

	private String elev_id;

	private String elev_name;

	private String elev_type;

	private StopEntity stop;

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

	public StopEntity getStop() {
		return stop;
	}

	public void setStop(StopEntity stop) {
		this.stop = stop;
	}

}
