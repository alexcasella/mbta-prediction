package com.mbta.test;

import java.util.List;

import com.mbta.graph.GraphBuilder;
import com.mbta.graph.HeuristicSearch;
import com.mbta.graph.MBTAgraph;
import com.mbta.graph.PathInfo;

public class GraphTest {

	public static void main(String[] args) throws Exception {

		MBTAgraph transportation = new MBTAgraph();

		try {
			System.out.println("Build graph...\n");
			GraphBuilder.loadAPIFile(transportation);
			System.out.println(transportation.getVertices().size()
					+ "Vertices, " + transportation.countEdges()
					+ " edges.\n\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Heuristic guided search
		// 70072: "Kendall/MIT - Outbound"
		// 70150: "Kenmore - Inbound"
		HeuristicSearch hs = new HeuristicSearch("70278", "70150", 5);
		List<PathInfo> shortestPath = hs.search(transportation);

		System.out.println(hs.printPath());

	}

}
