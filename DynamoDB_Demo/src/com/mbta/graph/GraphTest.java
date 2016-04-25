package com.mbta.test;

import java.util.ArrayList;
import java.util.List;

import com.mbta.graph.GraphBuilder;
import com.mbta.graph.HeuristicSearch;
import com.mbta.graph.MBTAgraph;
import com.mbta.graph.PathInfo;
import com.mbta.graph.TransferInfo;

public class GraphTest {

	public static void main(String[] args) throws Exception {

		// Heuristic guided search
		// 70072: "Kendall/MIT - Outbound"
		// 70150: "Kenmore - Inbound"
		
		// MBTAgraph transportation = MBTAgraph.getGraph();
		HeuristicSearch hs = new HeuristicSearch("70278", "70150", 5);
		List<PathInfo> shortestPath = hs.search(MBTAgraph.getGraph());

		System.out.println(hs.printPath());

		ArrayList<TransferInfo> transInfo = hs.getTransferInfo();
		for (TransferInfo eachTrans : transInfo) {
			System.out.println("Transfer at: " + eachTrans.getRoute_id() + ", "
					+ eachTrans.getStop_id() + ", " + eachTrans.getStop_name()
					+ ", Direction: " + eachTrans.getDirection_id());
		}

	}

}
