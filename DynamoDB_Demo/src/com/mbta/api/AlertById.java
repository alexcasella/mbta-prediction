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

import com.mbta.entity.AlertEntity;
import com.mbta.util.ConstDefine;

public class AlertById {
	public static AlertEntity getAlertById(String aldertID) throws Exception {
		AlertEntity alert = new AlertEntity();

		String endPointName = AlertById.class.getSimpleName().toLowerCase();

		String encodeAlertID = URLEncoder.encode(aldertID, "UTF-8").replace(
				"+", "%20");

		String parameter = new StringBuilder().append("id=")
				.append(encodeAlertID).toString();

		String requestURL = ConstDefine.mbtaBaseURI + endPointName
				+ ConstDefine.apiKey + parameter + ConstDefine.format;

		System.out.println("\nAlert By ID Request URL: \n" + requestURL + "\n");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			// Http request
			HttpGet httpGet = new HttpGet(requestURL);

			// Http response
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			System.out.println("Alert By Id " + httpResponse.getStatusLine());

			try {
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null
						&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String json = EntityUtils.toString(entity);
					// System.out.println("\nAlert By Id JSON:\n" + json +
					// "\n");

					ObjectMapper mapper = new ObjectMapper();

					alert = mapper.readValue(json, AlertEntity.class);

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

		return alert;

	}

}
