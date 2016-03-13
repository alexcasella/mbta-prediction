package com.mbta.api;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbta.entity.PredictionsByStopEntity;
import com.mbta.util.ConstDefine;

public class PredictionsByStop {

	public static PredictionsByStopEntity getPredictionByStop(String stopid)
			throws Exception {
		PredictionsByStopEntity prediction = new PredictionsByStopEntity();

		String endPointName = PredictionsByStop.class.getSimpleName()
				.toLowerCase();

		String encodeAlertID = URLEncoder.encode(stopid, "UTF-8").replace("+",
				"%20");

		String parameter = new StringBuilder().append("stop=")
				.append(encodeAlertID).toString();

		String requestURL = ConstDefine.mbtaBaseURI + endPointName
				+ ConstDefine.apiKey + parameter + ConstDefine.format;

		System.out.println("\nPredictions By Stop Request URL: \n" + requestURL
				+ "\n");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			// Http request
			HttpGet httpGet = new HttpGet(requestURL);

			// Http response
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			System.out.println("Prediction By Stop "
					+ httpResponse.getStatusLine());

			try {
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null
						&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String json = EntityUtils.toString(entity);
					// System.out.println("\nPredictions By Stop JSON:\n" + json + "\n");

					ObjectMapper mapper = new ObjectMapper();

					prediction = mapper.readValue(json,
							PredictionsByStopEntity.class);

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
