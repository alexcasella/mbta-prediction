package com.mbta.test;

import com.mbta.datastore.AllAlerts;
import com.mbta.datastore.AllStops;

public class Main {

	public static void main(String[] args) {

		// Thread timer = new Thread(new TimerTest());
		// timer.start();

		Thread allStops = new Thread(new AllStops());
		allStops.start();
		
		Thread allAlerts = new Thread(new AllAlerts());
		allAlerts.start();
	}

}
