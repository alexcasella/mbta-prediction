/**
 * 
 */
package com.mbta.graph;

import java.util.Comparator;

/**
 * @author andy
 *
 */
public class Arc {

	private static final double INFINITY = Double.POSITIVE_INFINITY;

	private Vertex from;

	private Vertex to;

	private String route_id;

	private String direction_id;

	private double distance;

	public Arc(Vertex from, Vertex to, String route_id, String direction_id) {
		this(from, to, route_id, direction_id, INFINITY);
	}

	public Arc(Vertex from, Vertex to, String route_id, String direction_id,
			double dist) {
		this.from = from;
		this.to = to;
		this.route_id = route_id;
		this.direction_id = direction_id;
		this.distance = dist;
	}

	public Vertex getFrom() {
		return from;
	}

	public void setFrom(Vertex from) {
		this.from = from;
	}

	public Vertex getTo() {
		return to;
	}

	public void setTo(Vertex to) {
		this.to = to;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public static Comparator<Arc> DistanceComparator = new Comparator<Arc>() {

		@Override
		public int compare(Arc e1, Arc e2) {
			double dist1 = e1.getDistance();
			double dist2 = e2.getDistance();
			// descending order
			return (int) (dist2 - dist1);
		}
	};
}
