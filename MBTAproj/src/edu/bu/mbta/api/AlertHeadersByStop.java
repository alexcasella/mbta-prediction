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

import edu.bu.mbta.entity.AlertHeadersByStopEntity;
import edu.bu.mbta.util.ConstDefine;

public class AlertHeadersByStop {

	public static AlertHeadersByStopEntity getAlertHeadersByStop(String stopid)
			throws Exception {
		AlertHeadersByStopEntity AlertHeaders = new AlertHeadersByStopEntity();

		String endPointName = AlertHeadersByStop.class.getSimpleName()
				.toLowerCase();

		String encodeAlertID = URLEncoder.encode(stopid, "UTF-8").replace("+",
				"%20");

		String parameter = new StringBuilder().append("stop=")
				.append(encodeAlertID).toString();

		String requestURL = ConstDefine.mbtaBaseURI + endPointName
				+ ConstDefine.apiKey1 + parameter + ConstDefine.format;

		System.out.println("\nAlert Headers By Stop Request URL: \n" + requestURL
				+ "\n");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			// Http request
			HttpGet httpGet = new HttpGet(requestURL);

			// Http response
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			System.out.println("Alert Headers By Stop "
					+ httpResponse.getStatusLine());

			try {
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null
						&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String json = EntityUtils.toString(entity);
					System.out.println("\nAlert Headers by Stop JSON:\n" + json + "\n");

					ObjectMapper mapper = new ObjectMapper();

					AlertHeaders = mapper.readValue(json,
							AlertHeadersByStopEntity.class);

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

		return AlertHeaders;
	}
}

