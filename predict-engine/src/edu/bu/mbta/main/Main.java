package edu.bu.mbta.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.bu.mbta.api.AlertById;
import edu.bu.mbta.entity.*;

public class Main {

	public static final String mbtaBaseURI = "http://realtime.mbta.com/developer/api/v2/";
	public static final String apiKey = "?api_key=TGmTHy9PgES2eVZlZ76Mjg&";
	public static final String format = "&format=json";

	public static void main(String args[]) {
		// just test how to make api call and retriece data.
		try {
			AlertEntity alert = AlertById.getAlertById("78216");

			System.out.println("Alert ID: " + alert.getAlert_id());
			System.out.println("Severity: " + alert.getSeverity());
			System.out.println("Alert Header Text: " + alert.getHeader_text());
			System.out
					.println("Total number of affected services: " + alert.getAffected_services().getServices().size());
		}

		catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

			System.out.println("Exception in Main.");
			return;
		}
	}

}
