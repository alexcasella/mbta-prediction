package edu.bu.mbta.api;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import edu.bu.mbta.entity.PredictionsByRouteEntity;
import edu.bu.mbta.entity.PredictionsByStopEntity;
import edu.bu.mbta.util.ConstDefine;

public class PredictionsByRoute {

	public static PredictionsByRouteEntity getPredictionByRoute(String routeid)
			throws Exception {
		PredictionsByRouteEntity prediction = new PredictionsByRouteEntity();

		String endPointName = PredictionsByRoute.class.getSimpleName()
				.toLowerCase();

		String encodeAlertID = URLEncoder.encode(routeid, "UTF-8").replace("+",
				"%20");

		String parameter = new StringBuilder().append("route=")
				.append(encodeAlertID).toString();
		
		String requestURL = "";
		if(routeid.equals("Blue")||routeid.equals("Red")||routeid.equals("Orange")) {
			requestURL = ConstDefine.mbtaBaseURI + endPointName
					+ ConstDefine.apiKey1 + parameter + ConstDefine.format;
		} else {
			requestURL = ConstDefine.mbtaBaseURI + endPointName
					+ ConstDefine.apiKey2 + parameter + ConstDefine.format;
		}

		System.out.println("\nPredictions By Stop Request URL: \n" + requestURL
				+ "\n");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			// Http request
			HttpGet httpGet = new HttpGet(requestURL);

			// Http response
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			System.out.println("Prediction By Route "
					+ httpResponse.getStatusLine());

			try {
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null
						&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String json = EntityUtils.toString(entity);
					// System.out.println("\nPredictions By Stop JSON:\n" + json + "\n");

					ObjectMapper mapper = new ObjectMapper();

					prediction = mapper.readValue(json,
							PredictionsByRouteEntity.class);

				} else {
					throw new Exception(httpResponse.getStatusLine()
							.getReasonPhrase());

				}
				EntityUtils.consume(entity);

			} finally {

				httpResponse.close();
			}

		} finally {
			httpclient.close();
		}

		return prediction;

	}
}


