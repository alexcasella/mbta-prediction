/**
 * 
 */
package com.mbta.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbta.api.StopsByRoute;
import com.mbta.entity.AllRoutesEntity;
import com.mbta.entity.DirectionEntity;
import com.mbta.entity.ModeEntity;
import com.mbta.entity.RouteEntity;
import com.mbta.entity.StopEntity;
import com.mbta.entity.StopsByRouteEntity;
import com.mbta.util.Distance;

/**
 * @author andy
 *
 */
public class GraphBuilder {
	public static MBTAgraph loadAPIFile(MBTAgraph g) throws Exception {
		// read json string from file
		BufferedReader reader = new BufferedReader(new FileReader(
				"AllRoutes.txt"));
		String json = reader.readLine();
		ObjectMapper mapper = new ObjectMapper();
		AllRoutesEntity allRoutes = new AllRoutesEntity();
		try {
			allRoutes = mapper.readValue(json, AllRoutesEntity.class);
		} catch (JsonMappingException e) {
			System.out.println("Json Mapping Exception in Routes "
					+ e.getCause());
		}

		// for each route find all the stops
		for (ModeEntity modeFromAllRoute : allRoutes.getMode()) {
			if (modeFromAllRoute.getRoute_type().equals("0")
					|| modeFromAllRoute.getRoute_type().equals("1")) {
				for (RouteEntity routeFromAllRoute : modeFromAllRoute
						.getRoute()) {
					reader = new BufferedReader(new FileReader(
							routeFromAllRoute.getRoute_id() + ".txt"));
					json = reader.readLine();
					StopsByRouteEntity stop_list = mapper.readValue(json,
							StopsByRouteEntity.class);

					// iterate on stop_list to create Vertex
					for (DirectionEntity directionFromSBR : stop_list
							.getDirection()) {
						// build the subgraph of a specific route
						GraphBuilder.build(directionFromSBR.getStop(), g,
								routeFromAllRoute.getRoute_id(),
								directionFromSBR.getDirection_id());
					}
				}

			}
		}
		return g;
	}

	private static void build(ArrayList<StopEntity> stop_list, MBTAgraph g,
			String route_id, String direction_id) {
		int first = 0, second = 0;
		for (first = 0; first < stop_list.size() && second < stop_list.size(); first++) {
			StopEntity startStop = stop_list.get(first);
			StopEntity nextStop = stop_list.get(second);

			while (startStop.getStop_order().equals(nextStop.getStop_order())) {
				second++;
				nextStop = stop_list.get(second);
			}

			int third = second;
			StopEntity nextnext = stop_list.get(third);
			while (nextStop.getStop_order().equals(nextnext.getStop_order())) {
				third++;
				if (third >= stop_list.size()) {
					nextnext = stop_list.get(stop_list.size() - 1);
					break;
				} else {
					nextnext = stop_list.get(third);
				}
			}

			for (int idxto = second; idxto < third; idxto++) {
				StopEntity dest = stop_list.get(idxto);
				for (int idxfrom = first; idxfrom < second; idxfrom++) {
					StopEntity current = stop_list.get(idxfrom);
					Vertex from = g.addVertex(current);
					Vertex to = g.addVertex(dest);
					double distance = Distance.computeDistance(from, to);
					g.addEdge(from, to, route_id, direction_id, distance);
				}
			}

			first = second - 1;
			second = third;
		}

		// add walk edge
		for (StopEntity stop : stop_list) {
			Vertex from = g.getVertex(stop.getStop_id());
			for (Vertex to : g.getVertices().values()) {
				if (!from.getID().equals(to.getID())) {
					if (from.getStop().getStop_lat()
							.equals(to.getStop().getStop_lat())
							&& from.getStop().getStop_lon()
									.equals(to.getStop().getStop_lon())) {
						// double distance = Distance.computeDistance(from, to);
						g.addEdge(from, to, "Walk", "0", 0);
						g.addEdge(to, from, "Walk", "0", 0);
					}
				}
			}
		}
	}
}
