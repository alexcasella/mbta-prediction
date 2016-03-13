package com.mbta.entity;

import java.util.ArrayList;

import com.mbta.entity.AffectedServicesEntity;
import com.mbta.entity.EffectPeriodEntity;

public class AlertEntity {

	private int alert_id;

	private String effect_name;

	private String effect;

	private String cause_name;

	private String cause;

	private String header_text;

	private String short_header_text;

	private String url;

	private String description_text;

	private String severity;

	private String created_dt;

	private String last_modified_dt;

	private String service_effect_text;

	private String timeframe_text;

	private String alert_lifecycle;

	private String banner_text;

	private ArrayList<EffectPeriodEntity> effect_periods;

	private AffectedServicesEntity affected_services;

	public int getAlert_id() {
		return alert_id;
	}

	public void setAlert_id(int alert_id) {
		this.alert_id = alert_id;
	}

	public String getEffect_name() {
		return effect_name;
	}

	public void setEffect_name(String effect_name) {
		this.effect_name = effect_name;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getCause_name() {
		return cause_name;
	}

	public void setCause_name(String cause_name) {
		this.cause_name = cause_name;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getHeader_text() {
		return header_text;
	}

	public void setHeader_text(String header_text) {
		this.header_text = header_text;
	}

	public String getShort_header_text() {
		return short_header_text;
	}

	public void setShort_header_text(String short_header_text) {
		this.short_header_text = short_header_text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription_text() {
		return description_text;
	}

	public void setDescription_text(String description_text) {
		this.description_text = description_text;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getCreated_dt() {
		return created_dt;
	}

	public void setCreated_dt(String created_dt) {
		this.created_dt = created_dt;
	}

	public String getLast_modified_dt() {
		return last_modified_dt;
	}

	public void setLast_modified_dt(String last_modified_dt) {
		this.last_modified_dt = last_modified_dt;
	}

	public String getService_effect_text() {
		return service_effect_text;
	}

	public void setService_effect_text(String service_effect_text) {
		this.service_effect_text = service_effect_text;
	}

	public String getTimeframe_text() {
		return timeframe_text;
	}

	public void setTimeframe_text(String timeframe_text) {
		this.timeframe_text = timeframe_text;
	}

	public String getAlert_lifecycle() {
		return alert_lifecycle;
	}

	public void setAlert_lifecycle(String alert_lifecycle) {
		this.alert_lifecycle = alert_lifecycle;
	}

	public String getBanner_text() {
		return banner_text;
	}

	public void setBanner_text(String banner_text) {
		this.banner_text = banner_text;
	}

	public ArrayList<EffectPeriodEntity> getEffect_periods() {
		return effect_periods;
	}

	public void setEffect_periods(ArrayList<EffectPeriodEntity> effect_periods) {
		this.effect_periods = effect_periods;
	}

	public AffectedServicesEntity getAffected_services() {
		return affected_services;
	}

	public void setAffected_services(AffectedServicesEntity affected_services) {
		this.affected_services = affected_services;
	}
}
