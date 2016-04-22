/**
 * 
 */
package com.mbta.graph;

/**
 * @author andy
 *
 */
public class PathInfo {

	private String stop_id;

	private String stop_name;

	private String route_id;

	private String direction_id;

	private double travel_distance;

	public PathInfo(String stop_id, String stop_name, String route_id,
			String direction_id, double travel_distance) {
		this.stop_id = stop_id;
		this.stop_name = stop_name;
		this.route_id = route_id;
		this.direction_id = direction_id;
		this.travel_distance = travel_distance;
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

	public String getRoute_id() {
		return route_id;
	}

	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	public String getDirection_id() {
		return direction_id;
	}

	public void setDirection_id(String direction_id) {
		this.direction_id = direction_id;
	}

	public double getTravel_distance() {
		return travel_distance;
	}

	public void setTravel_distance(double travel_distance) {
		this.travel_distance = travel_distance;
	}

}
