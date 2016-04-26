package org.siwan.mbta.messenger.resources;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.siwan.mbta.messenger.db.AlertDetailByStop;
import com.siwan.mbta.messenger.db.alertByStop;


@Path("/alertDetail")
public class AlertDetailResource {


	@GET
	@Path("/{Line}/{Start}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String Green(@PathParam("Line") String line, 
			            @PathParam("Start") String start){

			AlertDetailByStop db = new AlertDetailByStop();
			String alertdetail = db.query(start);
			return alertdetail;
		}

	}

