package com.mbta.api;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbta.entity.StopsByRouteEntity;
import com.mbta.util.ConstDefine;

public class StopsByRoute {

	public static StopsByRouteEntity getStop(String route_id)
			throws IOException {
		// declare the entity
		StopsByRouteEntity stop_list = new StopsByRouteEntity();
		// get the api name
		String endPointName = StopsByRoute.class.getSimpleName().toLowerCase();
		// using URLEncoder to encode the space in stopID
		String encodeRouteID = URLEncoder.encode(route_id, "UTF-8").replace(
				"+", "%20");
		// set the parameter
		String parameter = new StringBuilder().append("route=")
				.append(encodeRouteID).toString();
		// set the requestURL
		String requestURL = ConstDefine.mbtaBaseURI + endPointName
				+ ConstDefine.apiKey2 + parameter + ConstDefine.format;
		// requestURL = requestURL.replaceAll(" ", "%20");
		// System.out.println("\nStops By Route Request URL: \n" + requestURL);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// MBTA API call returns results in a JSON format
			HttpGet httpGet = new HttpGet(requestURL);

			// Execute HTTP Request
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			try {
				// Get hold of the response entity
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null) {
					// convert to Json String
					String json = EntityUtils.toString(entity);
					// System.out.println("\nStops By Route " + route_id +
					// " JSON:\n" + json + "\n");
					
					// write json to a txt file
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							route_id + ".txt"));
					writer.write(json);
					writer.close();

					ObjectMapper mapper = new ObjectMapper();
					// JSON from String to Object
					stop_list = mapper
							.readValue(json, StopsByRouteEntity.class);
				}
				// release all resources held by the httpEntity
				EntityUtils.consume(entity);
			} finally {
				// When HttpClient instance is no longer needed,
				// shut down the connection manager to ensure
				// immediate deallocation of all system resources
				httpResponse.close();
			}
		} catch (IOException ex) {
			Logger.getLogger(StopsByRouteEntity.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			httpclient.close();
		}
		return stop_list;

	}

}
