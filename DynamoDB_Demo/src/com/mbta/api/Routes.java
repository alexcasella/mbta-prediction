package com.mbta.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbta.entity.AllRoutesEntity;
import com.mbta.util.ConstDefine;

public class Routes {

	public static AllRoutesEntity getAllRoutes() throws Exception {
		// declare the entity
		AllRoutesEntity allRouteList = new AllRoutesEntity();
		// get the api name
		String endPointName = Routes.class.getSimpleName().toLowerCase();
		// set the requestURL
		String requestURL = ConstDefine.mbtaBaseURI + endPointName
				+ ConstDefine.apiKey + ConstDefine.format.substring(1);
		System.out.println("\nAll Routes Request URL:\n" + requestURL);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// MBTA API call returns results in a JSON format
			HttpGet httpGet = new HttpGet(requestURL);

			// Execute HTTP Request
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			// Get hold of the response entity
			HttpEntity entity = httpResponse.getEntity();

			if (entity != null
					&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// convert to Json String
				String json = EntityUtils.toString(entity);
				System.out.println("\nRoutes JSON:\n" + json + "\n");

				ObjectMapper mapper = new ObjectMapper();
				// JSON from String to Object
				try {
					allRouteList = mapper
							.readValue(json, AllRoutesEntity.class);
				} catch (JsonMappingException e) {
					System.out.println("Json Mapping Exception in Routes "
							+ e.getCause());
				}
			} else {
				throw new Exception(httpResponse.getStatusLine()
						.getReasonPhrase());
			}
			// release all resources held by the httpEntity
			EntityUtils.consume(entity);

			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpResponse.close();

			httpclient.close();
		} catch (IOException ex) {
			Logger.getLogger(Routes.class.getName())
					.log(Level.SEVERE, null, ex);
		}

		return allRouteList;
	}

}
