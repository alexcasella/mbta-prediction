package edu.bu.mbta.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertHeadersEntity {

	private AlertEntity alert = new AlertEntity();

	public AlertEntity getAlert() {
		return alert;
	}

	public void setAlert(AlertEntity alert) {
		this.alert = alert;
	}

}
