/**
 * 
 */
package com.mbta.util;

import java.text.DecimalFormat;

import com.mbta.graph.Vertex;

/**
 * @author andy
 *
 */
public class Distance {

	public static double computeDistance(Vertex from, Vertex to) {
		return distance(from.getStop().getStop_lat(), from.getStop()
				.getStop_lon(), to.getStop().getStop_lat(), to.getStop()
				.getStop_lon());
	}

	private static double distance(String slat1, String slon1, String slat2,
			String slon2) {
		double lat1 = Double.parseDouble(slat1);
		double lon1 = Double.parseDouble(slon1);
		double lat2 = Double.parseDouble(slat2);
		double lon2 = Double.parseDouble(slon2);

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		return Double.parseDouble(new DecimalFormat("#.####").format(dist));
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
