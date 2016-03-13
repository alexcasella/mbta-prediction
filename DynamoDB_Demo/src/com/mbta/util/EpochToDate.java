package com.mbta.util;

public class EpochToDate {

	public static String toDate(String epoch_time) {
		long epoch = Long.valueOf(epoch_time);
		String date = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(epoch * 1000));

		return date;

	}
}
