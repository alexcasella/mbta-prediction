package edu.bu.mbta.api;

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

import edu.bu.mbta.entity.AlertByIdEntity;
import edu.bu.mbta.entity.AlertEntity;
import edu.bu.mbta.main.Main;

public class AlertById {
//	public static String getAlertById(String aldertID) throws Exception {
	public static AlertEntity getAlertById(String aldertID) throws Exception {
		AlertEntity alert = new AlertEntity();
		String json = "";
		
		String endPointName = AlertById.class.getSimpleName().toLowerCase();

		String encodeAlertID = URLEncoder.encode(aldertID, "UTF-8").replace("+", "%20");

		String parameter = new StringBuilder().append("id=").append(encodeAlertID).toString();

		String requestURL = Main.mbtaBaseURI + endPointName + Main.apiKey + parameter + Main.format;

		System.out.println("\nAlert By ID Request URL: \n" + requestURL + "\n");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpGet httpGet = new HttpGet(requestURL);

			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			try {
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					json = EntityUtils.toString(entity);
					// System.out.println("\nAlert By Id JSON:\n" + json +
					// "\n");

					ObjectMapper mapper = new ObjectMapper();

					alert = mapper.readValue(json, AlertEntity.class);

				} else {
					throw new Exception(httpResponse.getStatusLine().getReasonPhrase());
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
