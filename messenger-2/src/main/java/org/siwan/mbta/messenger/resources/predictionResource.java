package org.siwan.mbta.messenger.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mbta.graph.GraphBuilder;
import com.mbta.graph.HeuristicSearch;
import com.mbta.graph.MBTAgraph;
import com.mbta.graph.PathInfo;
import com.mbta.graph.TransferInfo;
import com.siwan.mbta.messenger.db.AlertDetailByStop;
import com.siwan.mbta.messenger.db.alertByStop;
import com.siwan.mbta.messenger.db.predictionByRoute;

@Path("/Allalerts")
public class predictionResource {

//get alert header
	@GET
	@Path("/{Info}/{Start}/{End}")
	@Produces(MediaType.APPLICATION_JSON)
	public String Green(@PathParam("Start") String start,
						@PathParam("End") String end,
						@PathParam("Info") String info
			){
		
		if(info.equals("header")) {
			alertByStop db = new alertByStop();
			String alert = db.query(start);
			return alert;
		} else {		
			AlertDetailByStop db2 = new AlertDetailByStop();
			String alertdetail = db2.query(start);
			return alertdetail;
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
//		if(transInfo.size() == 0) {	
//			if(info.equals("header")) {
//				alertByStop db = new alertByStop();
//				String alert = db.query(start);
//				return alert;
//			} else {		
//				AlertDetailByStop db2 = new AlertDetailByStop();
//				String alertdetail = db2.query(start);
//				return alertdetail;
//			}
//		}
/////****************test*****************/		
//				
//		String result = "";
//		ArrayList<String> stopid = new ArrayList<>();
//		ArrayList<String> stopname = new ArrayList<>();
//		ArrayList<String> direction = new ArrayList<>();
//	
//		if(info.equals("header")) {
//			if(transInfo.size() == 1) {
//				result = "There are one transfer choice for you: ";
//			}else {
//				result = "There are "+ transInfo.size() + "transfer choices for you: ";
//			}
//		}
//	
//		while(transInfo.size()>0) {
//			int index = transInfo.size() -1;
//			TransferInfo eachTrans = transInfo.remove(index);
//			if(info.equals("header")) {
//			result += "Transfer at "+ eachTrans.getRoute_id() + ", " + eachTrans.getStop_name() +" station";
//				if(transInfo.size()==0) {
//					result += ". ";
//				} else {
//					result += "; ";
//				}
//			}
//			stopid.add(eachTrans.getStop_id());
//			stopname.add(eachTrans.getStop_name());
//			direction.add(eachTrans.getDirection_id());
//		}
//		
//		alertByStop db = new alertByStop();
//		AlertDetailByStop db2 = new AlertDetailByStop();
//		if(info.equals("header")) {
//			String alert = db.query(start);
//			result = result + alert;
//		} else {
//			String alertdetail = db2.query(start);
//			result = result + alertdetail;
//		}
//	
//		while(stopname.size() > 0 && stopid.size() > 0) {
//			int index1 = stopname.size() -1;
//			int index2 = stopid.size() -1;
//			String getname = stopname.remove(index1);
//			String getid = stopid.remove(index2);
//			result+="If you choose to transfer at "+ getname +", ";
//			if(info.equals("header")) {
//				String alert1 = db.query(getid);
//				result+= alert1;
//			} else {
//				String alertdetail1 = db2.query(getid);
//				result+= alertdetail1;
//			}
//		
//		}
		

//		return result;
	

	}
	
	
	

	//get path
		@GET
		@Path("/{Start}/{End}")
		@Produces(MediaType.APPLICATION_JSON)
		public String Green1(@PathParam("Start") String start,
							@PathParam("End") String end
				){
			
			MBTAgraph transportation = new MBTAgraph();

			try {
				System.out.println("Build graph...\n");
				GraphBuilder gb = new GraphBuilder();
				gb.loadAPIFile(transportation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			HeuristicSearch hs = new HeuristicSearch(start, end, 5);
		
			try {
				List<PathInfo> shortestPath = hs.search(transportation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<TransferInfo> transInfo = hs.getTransferInfo();
			String result = "";
			if(transInfo.size() == 0) {	
			    result = "You don't need to transfer. ";
			} else if(transInfo.size() == 1) {
					result = "There is one transfer choice for you: ";
			}else {
					result = "There are "+ transInfo.size() + "transfer choices for you: ";
			}
			
			while(transInfo.size()>0) {
				int index = transInfo.size() -1;
				TransferInfo eachTrans = transInfo.remove(index);
				result += "Transfer at "+ eachTrans.getRoute_id() + ", " + eachTrans.getStop_name() +" station";
					if(transInfo.size()==0) {
						result += ". ";
					} else {
						result += "; ";
					}
			}
				
			return result;
			
		}
		
	
	
}



