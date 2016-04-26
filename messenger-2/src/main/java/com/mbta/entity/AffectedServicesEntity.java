package com.mbta.entity;

import java.util.ArrayList;

import com.mbta.entity.ElevatorEntity;
import com.mbta.entity.ServiceEntity;

public class AffectedServicesEntity {
	private ArrayList<ServiceEntity> services;

	private ArrayList<ElevatorEntity> elevators;

	public ArrayList<ServiceEntity> getServices() {
		return services;
	}

	public void setServices(ArrayList<ServiceEntity> services) {
		this.services = services;
	}

	public ArrayList<ElevatorEntity> getElevators() {
		return elevators;
	}

	public void setElevators(ArrayList<ElevatorEntity> elevators) {
		this.elevators = elevators;
	}
}
