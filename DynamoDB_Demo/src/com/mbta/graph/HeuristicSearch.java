/**
 * 
 */
package com.mbta.graph;

import java.util.ArrayList;
import java.util.List;

import com.mbta.util.ConstDefine;
import com.mbta.util.Distance;

/**
 * @author andy
 *
 */
public class HeuristicSearch {

	private List<HeuristicInfo> frontier;

	private List<HeuristicInfo> explored;

	private double Dmin;

	private int maxTransfer;

	private String startId;

	private String endId;

	public List<PathInfo> shortestPath;

	public HeuristicSearch(String start, String end, int transfer) {

		this.frontier = new ArrayList<>();

		this.explored = new ArrayList<>();

		this.Dmin = Double.POSITIVE_INFINITY;

		this.shortestPath = new ArrayList<>();

		this.startId = start;

		this.endId = end;

		this.maxTransfer = transfer;
	}

	public List<PathInfo> search(MBTAgraph g) throws Exception {
		System.out.println("Search path begins...\n");
		double dist = Distance.computeDistance(g.getVertex(startId),
				g.getVertex(endId));
		Dmin = Math.min(dist / ConstDefine.WALK_SPEED, Dmin);

		addFrontier(g);
		while (frontier.size() > 0) {
			int index = findMinTimeIndex(frontier);
			HeuristicInfo current = frontier.get(index);
			// System.out.println("Frontier get: " +
			// current.getStop().getName());

			if (current.getD_estimate() > Dmin) {
				return shortestPath;
			}

			// check transfer time
			boolean moreTransfer = false;
			if (current.getD_real() <= Dmin) {
				if (current.getD_real() == Dmin) {
					if (countTransfer(current.path) > countTransfer(shortestPath)) {
						moreTransfer = true;
					}
				}
				if (!moreTransfer) {
					Dmin = current.getD_real();
					shortestPath.clear();
					for (int i = 0; i < current.path.size() - 1; i++) {
						PathInfo p_path = current.path.get(i);
						String current_lat = g.getVertex(p_path.getStop_id())
								.getStop().getStop_lat();
						String current_lon = g.getVertex(p_path.getStop_id())
								.getStop().getStop_lon();

						PathInfo next = current.path.get(i + 1);
						String last_lat = g.getVertex(next.getStop_id())
								.getStop().getStop_lat();
						String last_lon = g.getVertex(next.getStop_id())
								.getStop().getStop_lon();

						if (current_lat.equals(last_lat)
								&& current_lon.equals(last_lon)
								&& p_path.getRoute_id().equals(
										next.getRoute_id())) {
							continue;
						}
						shortestPath.add(p_path);
					}
					shortestPath.add(current.path.get(current.path.size() - 1));

				}
			}

			double deltaD = 0.0;
			for (Arc e : current.getStop().getOutgoingArcs()) {
				boolean hasExplored = false;
				Vertex next = e.getTo();
				String next_id = next.getID();
				// System.out.println(next.getName());

				for (int i = 0; i < explored.size(); i++) {
					HeuristicInfo oldInfo = explored.get(i);
					if (next_id.equals(oldInfo.getStop().getID())) {
						hasExplored = true;

						// calculate the distance from current to next
						HeuristicInfo newInfo = new HeuristicInfo();
						newInfo.setStop(next);
						double t = 0.0;
						if (e.getRoute_id().equals("Walk")) {
							t = e.getDistance() / ConstDefine.WALK_SPEED;
							newInfo.setD_start_current(current
									.getD_start_current() + t);

						} else {
							t = e.getDistance() / ConstDefine.TRAIN_SPEED;
							newInfo.setD_start_current(current
									.getD_start_current() + t);
						}

						// compare new travel dist with old one
						// if the new one smaller, update path and all paths
						// have current node
						if (newInfo.getD_start_current() < oldInfo
								.getD_start_current()) {
							deltaD = oldInfo.getD_start_current()
									- newInfo.getD_start_current();
							// update C information
							oldInfo.setD_start_current(oldInfo
									.getD_start_current() - deltaD);

							// find other Explored vertex whose path has C
							for (int j = 0; j < explored.size(); j++) {
								List<PathInfo> tempPath = explored.get(j).path;
								for (int k = 0; k < tempPath.size(); k++) {
									PathInfo p = tempPath.get(k);
									if (p.getStop_id().equals(
											oldInfo.getStop().getID())) {
										List<PathInfo> newPath = new ArrayList<PathInfo>();
										explored.get(j).setD_start_current(
												explored.get(j)
														.getD_start_current()
														- deltaD);
										for (PathInfo h : current.path) {
											newPath.add(h);
										}
										PathInfo update = new PathInfo(next_id,
												explored.get(i).getStop()
														.getName(),
												e.getRoute_id(),
												e.getDirection_id(), t);
										newPath.add(update);

										for (int m = k + 1; m < tempPath.size(); m++) {
											newPath.add(explored.get(j).path
													.get(m));
										}
										explored.get(j).path.clear();
										for (PathInfo h : newPath) {
											explored.get(j).path.add(h);
										}
									}
								}
							}
						}
					}
				}

				if (!hasExplored) {
					HeuristicInfo temp = new HeuristicInfo();
					temp.setStop(next);
					double t = 0.0;
					if (e.getRoute_id().equals("Walk")) {
						t = e.getDistance() / ConstDefine.WALK_SPEED;
						temp.setD_start_current(current.getD_start_current()
								+ t);

					} else {
						t = e.getDistance() / ConstDefine.TRAIN_SPEED;
						temp.setD_start_current(current.getD_start_current()
								+ t);
					}

					double dist_c_end = Distance.computeDistance(
							g.getVertex(endId), next);
					temp.setD_current_end_walk(dist_c_end
							/ ConstDefine.WALK_SPEED);
					temp.setD_current_end_train(dist_c_end
							/ ConstDefine.TRAIN_SPEED);
					temp.setD_real(temp.getD_start_current()
							+ temp.getD_current_end_walk());
					temp.setD_estimate(temp.getD_start_current()
							+ temp.getD_current_end_train());

					for (PathInfo p_path : current.path) {
						temp.path.add(p_path);
					}

					temp.path.add(new PathInfo(next.getID(), next.getName(), e
							.getRoute_id(), e.getDirection_id(), t));

					if (countTransfer(temp.path) <= maxTransfer) {
						frontier.add(temp);
						// System.out.println("Frontier add: "
						// + temp.getStop().getName());
					}
				}
			}

			explored.add(frontier.get(index));
			frontier.remove(index);
		}

		return shortestPath;
	}

	/**
	 * @param path
	 * @return
	 */
	private int countTransfer(List<PathInfo> path) {
		int totalTransfer = 0;
		// do not count the walk to start stop
		for (int i = 1; i < path.size() - 1; i++) {
			if (!path.get(i).getRoute_id()
					.equals(path.get(i + 1).getRoute_id())) {
				totalTransfer++;
			}
		}
		return totalTransfer;
	}

	/**
	 * @param frontier
	 * @return
	 */
	private int findMinTimeIndex(List<HeuristicInfo> frontier) {
		int index = 0;
		double temp = Double.POSITIVE_INFINITY;
		for (int i = 0; i < frontier.size(); i++) {
			if (temp > frontier.get(i).getD_estimate()) {
				index = i;
				temp = frontier.get(i).getD_estimate();
			}
		}
		return index;
	}

	/**
	 * @param g
	 */
	private void addFrontier(MBTAgraph g) {
		Vertex currentVertex = g.getVertex(startId);

		HeuristicInfo info = new HeuristicInfo();
		info.setStop(currentVertex);
		info.setD_start_current(0);
		double dist = Distance.computeDistance(g.getVertex(endId),
				currentVertex);
		info.setD_current_end_walk(dist / ConstDefine.WALK_SPEED);
		info.setD_current_end_train(dist / ConstDefine.TRAIN_SPEED);
		info.setD_real(info.getD_start_current() + info.getD_current_end_walk());
		info.setD_estimate(info.getD_start_current()
				+ info.getD_current_end_train());
		info.path.add(new PathInfo(currentVertex.getID(), currentVertex
				.getName(), "Walk", "0", 0));
		// System.out.println("Add frontier: " + info.getStop().getName());
		frontier.add(info);
	}

	public String printPath() {
		StringBuilder sb = new StringBuilder();
		sb.append("Shortest Path: \n\n");
		for (int i = 0; i < shortestPath.size() - 1; i++) {
			PathInfo current = shortestPath.get(i);
			PathInfo next = shortestPath.get(i + 1);

			sb.append("\"").append(current.getRoute_id()).append("\" To \"")
					.append(current.getStop_name()).append("\"\n");

			sb.append("-->\n");
		}

		sb.append("\"")
				.append(shortestPath.get(shortestPath.size() - 1).getRoute_id())
				.append("\" To \"")
				.append(shortestPath.get(shortestPath.size() - 1)
						.getStop_name()).append("\"\n\n");
		sb.append("Total transfer: " + countTransfer(shortestPath)
				+ " times.\n");

		return sb.toString();
	}
}
