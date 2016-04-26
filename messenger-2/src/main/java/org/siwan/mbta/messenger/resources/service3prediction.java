package org.siwan.mbta.messenger.resources;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.siwan.mbta.messenger.db.predictionByRoute;
import com.siwan.mbta.messenger.db.predictionByStop;
@Path("/Service3Prediction")
public class service3prediction {


	@GET
	@Path("/{Line}/{Start}/{Time}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String Green(@PathParam("Line") String line, 
			            @PathParam("Start") String start,
			            @PathParam("Time") String time){
		
//		long yourmilliseconds = System.currentTimeMillis();
//		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
//		Date resultdate = new Date(yourmilliseconds);
//		String date = sdf.format(resultdate);
//		String hour = date.substring(date.lastIndexOf(" ")+1, date.indexOf(":"));

//		if(Integer.parseInt(time) - Integer.parseInt(hour) > 1) {		
			predictionByStop db = new predictionByStop();
			String prediction = db.query(line, start, time);
			return prediction;
//		} else {
//			predictionByRoute db = new predictionByRoute();
//			String prediction = db.query(line, start);
//			return prediction;
//		}
	}
}
