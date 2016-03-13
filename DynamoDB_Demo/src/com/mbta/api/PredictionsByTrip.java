package com.mbta.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbta.entity.PredictionsByTripEntity;
import com.mbta.util.ConstDefine;

public class PredictionsByTrip {
	public static PredictionsByTripEntity getPredictions(String tripID)
			throws Exception {
		// declare the entity
		PredictionsByTripEntity prediction = new PredictionsByTripEntity();
		// get the api name
		String endPointName = PredictionsByTrip.class.getSimpleName()
				.toLowerCase();
		// using URLEncoder to encode the space in stopID
		String encodeTripID = URLEncoder.encode(tripID, "UTF-8").replace("+",
				"%20");
		// get the parameter
		String parameter = new StringBuilder().append("trip=")
				.append(encodeTripID).toString();
		// set the requestURL
		String requestURL = ConstDefine.mbtaBaseURI + endPointName
				+ ConstDefine.apiKey + parameter + ConstDefine.format;
		System.out.println("\nPredictions By Trip Request URL: \n" + requestURL
				+ "\n");

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// MBTA API call returns results in a JSON format
			HttpGet httpGet = new HttpGet(requestURL);

			// Execute HTTP Request
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			System.out.println("Predictions By Trip "
					+ httpResponse.getStatusLine());

			try {
				// Get hold of the response entity
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null
						&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// convert to Json String
					String json = EntityUtils.toString(entity);
					// System.out.println("\nPredictions By Trip JSON:\n" + json
					// + "\n");

					ObjectMapper mapper = new ObjectMapper();
					// JSON from String to Object
					prediction = mapper.readValue(json,
							PredictionsByTripEntity.class);
				} else {
					// System.out.println(httpResponse.getStatusLine().getReasonPhrase());
					throw new Exception(httpResponse.getStatusLine()
							.getReasonPhrase());
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
			Logger.getLogger(PredictionsByStop.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			httpclient.close();
		}
		return prediction;
	}
}
