package org.siwan.mbta.messenger.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.siwan.mbta.messenger.db.AlertDetailByStop;
import com.siwan.mbta.messenger.db.alertByStop;
import com.siwan.mbta.messenger.db.predictionByRoute;
import com.siwan.mbta.messenger.db.predictionByStop;
import com.mbta.graph.*;


@Path("/AllPrediction")
public class MessageResource {
		
//Get service3 time prediction
	@GET
	@Path("/{Line}/{Start}/{End}/{Time}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String Green2(@PathParam("Line") String line, 
			            @PathParam("Start") String start,
			            @PathParam("End") String end,
			            @PathParam("Time") String time){
		
		predictionByStop db = new predictionByStop();
		String prediction = db.query(line, start, time);
		return prediction;
	}

//		MBTAgraph transportation = new MBTAgraph();
//
//		try {
//			System.out.println("Build graph...\n");
//			GraphBuilder gb = new GraphBuilder();
//			gb.loadAPIFile(transportation);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	
//		HeuristicSearch hs = new HeuristicSearch(start, end, 5);
//	
//		try {
//			List<PathInfo> shortestPath = hs.search(transportation);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		ArrayList<TransferInfo> transInfo = hs.getTransferInfo();
//		
//		if(transInfo.size() == 0) {
//			predictionByStop db = new predictionByStop();
//			String prediction = db.query(line, start, time);
//			return prediction;
//		}
//		
//		String result = "";
//		ArrayList<String> stopid = new ArrayList<>();
//		ArrayList<String> stopname = new ArrayList<>();
//		ArrayList<String> direction = new ArrayList<>();
//		ArrayList<String> routeid = new ArrayList<>();
	
	
//		if(transInfo.size() == 1) {
//			result = "There is one transfer choice for you: ";
//		}else {
//			result = "There are "+ transInfo.size() + "transfer choices for you: ";
//		}
	
//		while(transInfo.size()>0) {
//			int index = transInfo.size() -1;
//			TransferInfo eachTrans = transInfo.remove(index);
//			result += "Transfer at "+ eachTrans.getRoute_id() + ", " + eachTrans.getStop_name() +" station";
//			if(transInfo.size()==0) {
//				result += ". ";
//			} else {
//				result += "; ";
//			}
//			stopid.add(eachTrans.getStop_id());
//			stopname.add(eachTrans.getStop_name());
//			direction.add(eachTrans.getDirection_id());
//			routeid.add(eachTrans.getRoute_id());
//		}
		
//		predictionByStop db = new predictionByStop();
//		String prediction = db.query(line, start, time);
//		result = result + prediction;
	
//		while(stopname.size() > 0 && stopid.size() > 0 && routeid.size() > 0) {
//			int index1 = stopname.size() -1;
//			int index2 = stopid.size() -1;
//			int index3 = routeid.size() -1;
//			String getname = stopname.remove(index1);
//			String getid = stopid.remove(index2);
//			String getroute = routeid.remove(index3);
//			int p = getroute.indexOf("-");
//			if (p != -1) {
//				String s1 = getroute.substring(0, p);
//				String s2 = getroute.substring(p+1);
//				getroute =  s1+s2; 
//			}			
//			result+="If you choose to transfer at "+ getname +", ";
//			String prediction2 = db.query(getroute, getid, time);
//			result = result + prediction2;
//
//		}
//		return result;
		
		
		
		
/*		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(yourmilliseconds);
		String date = sdf.format(resultdate);
		String hour = date.substring(date.lastIndexOf(" ")+1, date.indexOf(":"));

		if(Integer.parseInt(time) - Integer.parseInt(hour) > 1) {		
			predictionByStop db = new predictionByStop();
			String prediction = db.query(line, start, time);
			return prediction;
		} else {
			predictionByRoute db = new predictionByRoute();
			String prediction = db.query(line, start);
			return prediction;
		}*/
//	}
	


	
//Get Service1 Prediction
	@GET
	@Path("/{Line}/{Start}/{End}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String Green3(@PathParam("Line") String line, 
			            @PathParam("Start") String start,
			            @PathParam("End") String end
			            ){
		
		predictionByRoute db2 = new predictionByRoute();
		String prediction2 = db2.query(line, start);
		return prediction2;
		
//		MBTAgraph transportation = new MBTAgraph();

//		try {
//			System.out.println("Build graph...\n");
//			GraphBuilder gb = new GraphBuilder();
//			gb.loadAPIFile(transportation);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	
//		HeuristicSearch hs = new HeuristicSearch(start, end, 5);
//	
//		try {
//			List<PathInfo> shortestPath = hs.search(transportation);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		ArrayList<TransferInfo> transInfo = hs.getTransferInfo();
//		predictionByRoute db = new predictionByRoute();
//		
//		if(transInfo.size() == 0) {			
//			String time = db.query(line, start);
//			return time;
//		}
//		
//		String result = "";
//		ArrayList<String> stopid = new ArrayList<>();
//		ArrayList<String> stopname = new ArrayList<>();
//		ArrayList<String> direction = new ArrayList<>();
//		ArrayList<String> routeid = new ArrayList<>();
	
	
//		if(transInfo.size() == 1) {
//			result = "There is one transfer choice for you: ";
//		}else {
//			result = "There are "+ transInfo.size() + "transfer choices for you: ";
//		}
	
//		while(transInfo.size()>0) {
//			int index = transInfo.size() -1;
//			TransferInfo eachTrans = transInfo.remove(index);
//			result += "Transfer at "+ eachTrans.getRoute_id() + ", " + eachTrans.getStop_name() +" station";
//			if(transInfo.size()==0) {
//				result += ".";
//			} else {
//				result += "; ";
//			}
//			stopid.add(eachTrans.getStop_id());
//			stopname.add(eachTrans.getStop_name());
//			direction.add(eachTrans.getDirection_id());
//			routeid.add(eachTrans.getRoute_id());
//		}
//		
//		String prediction = db.query(line, start);
//		result = result + prediction;
//	
//		while(stopname.size() > 0 && stopid.size() > 0 && routeid.size() > 0) {
//			int index1 = stopname.size() -1;
//			int index2 = stopid.size() -1;
//			int index3 = routeid.size() -1;
//			String getname = stopname.remove(index1);
//			String getid = stopid.remove(index2);
//			String getroute = routeid.remove(index3);
//			int p = getroute.indexOf("-");
//			if (p != -1) {
//				String s1 = getroute.substring(0, p);
//				String s2 = getroute.substring(p+1);
//				getroute =  s1+s2; 
//			}
//			result+="If you choose to transfer at "+ getname +", ";
//			String prediction2 = db.query(getroute, getid);
//			result = result + prediction2;
//		}
//		return result;
	}

}
	
