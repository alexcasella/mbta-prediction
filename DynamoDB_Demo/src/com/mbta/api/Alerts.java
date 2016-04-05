package com.mbta.api;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbta.entity.AlertEntity;
import com.mbta.entity.AllAlertsEntity;
import com.mbta.util.ConstDefine;

public class Alerts {

	public static AllAlertsEntity getAllAlerts(boolean include_access_alerts,
			boolean include_service_alerts) throws Exception {
		AllAlertsEntity alerts = new AllAlertsEntity();

		String endPointName = Alerts.class.getSimpleName().toLowerCase();
		String parameter = new StringBuilder().append("include_access_alerts=")
				.append(include_access_alerts)
				.append("&include_service_alerts=")
				.append(include_service_alerts).toString();
		String requestURL = ConstDefine.mbtaBaseURI + endPointName
				+ ConstDefine.apiKey + parameter + ConstDefine.format;

		// System.out.println("\nAlert Request URL: \n" + requestURL + "\n");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpGet httpGet = new HttpGet(requestURL);

			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

			try {
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null
						&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String json = EntityUtils.toString(entity);
					// System.out.println("\nAlerts JSON:\n" + json + "\n");

					ObjectMapper mapper = new ObjectMapper();
					alerts = mapper.readValue(json, AllAlertsEntity.class);

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

		return alerts;
	}
}
