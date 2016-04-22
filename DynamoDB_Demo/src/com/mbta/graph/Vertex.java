/**
 * 
 */
package com.mbta.graph;

import java.util.ArrayList;
import java.util.List;

import com.mbta.entity.StopEntity;

/**
 * @author andy
 *
 */
public class Vertex {

	private final StopEntity stop;

	private List<Arc> incomingArcs;

	private List<Arc> outgoingArcs;

	// public Vertex predecessor;
	public Vertex(StopEntity stop) {
		this.stop = stop;
		incomingArcs = new ArrayList<>();
		outgoingArcs = new ArrayList<>();
	}

	// add edge to a vertex
	public boolean addArc(Arc e) {
		if (e.getFrom() == this) {
			outgoingArcs.add(e);
		} else if (e.getTo() == this) {
			incomingArcs.add(e);
		} else {
			return false;
		}
		return true;
	}

	public void addIncomingArc(Vertex from, String route_id, String direction_id) {
		Arc in = new Arc(from, this, route_id, direction_id);
		incomingArcs.add(in);
	}

	public void addOutgoingArc(Vertex to, String route_id, String direction_id) {
		Arc out = new Arc(this, to, route_id, direction_id);
		outgoingArcs.add(out);
	}

	public String getID() {
		return stop.getStop_id();
	}

	public StopEntity getStop() {
		return stop;
	}

	public String getName() {
		return stop.getStop_name();
	}

	public List<Arc> getIncomingArcs() {
		return incomingArcs;
	}

	public List<Arc> getOutgoingArcs() {
		return outgoingArcs;
	}

	public Arc findArc(Vertex destination, String route_id, String direction_id) {
		for (Arc e : this.outgoingArcs) {
			if (e.getTo() == destination && e.getRoute_id().equals(route_id)
					&& e.getDirection_id().equals(direction_id))
				return e;
		}
		return null;
	}
}
