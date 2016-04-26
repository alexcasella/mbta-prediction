/**
 * 
 */
package com.mbta.graph;

import java.util.HashMap;

import com.mbta.entity.StopEntity;

/**
 * @author andy
 *
 */
public class MBTAgraph {

	private HashMap<String, Vertex> vertices;

	// Construct a new graph without any vertices or edges
	public MBTAgraph() {
		this.vertices = new HashMap<>();
	}

	// add a vertex to the graph
	public Vertex addVertex(StopEntity stop) {
		Vertex v = null;
		if (!vertices.containsKey(stop.getStop_id())) {
			v = new Vertex(stop);
			vertices.put(stop.getStop_id(), v);
		} else {
			v = vertices.get(stop.getStop_id());
		}
		return v;
	}

	// returns a specific vertex
	public Vertex getVertex(String stop_id) {
		return vertices.get(stop_id);
	}

	// returns true iff vertex is in this Graph
	public boolean hasVertex(Vertex v) {
		return vertices.containsKey(v.getStop().getStop_id());
	}

	public HashMap<String, Vertex> getVertices() {
		return this.vertices;
	}

	// Add a edge to graph
	public boolean addEdge(Vertex from, Vertex to, String route_id,
			String direction_id, double distance) {
		if (!vertices.containsKey(from.getID())
				|| !vertices.containsKey(to.getID())) {
			throw new IllegalArgumentException(
					"Both nodes must be in the graph.");
		}

		Arc e = new Arc(from, to, route_id, direction_id, distance);
		// if Vertex from already has edge to Vertex to, do nothing
		// else add edge
		if (from.findArc(to, route_id, direction_id) == null) {
			from.addArc(e);
			to.addArc(e);
			// System.out.println("from " + e.getFrom().getID() + " to "
			// + e.getTo().getID() + " by " + e.getRoute_id() + ", dir "
			// + e.getDirection_id() + " with d = " + e.getDistance());
			return true;
		} else {
			return false;
		}
	}

	public int countEdges() {
		int edges = 0;
		for (Vertex v : vertices.values()) {
			edges += v.getIncomingArcs().size();
		}
		return edges;
	}
}
