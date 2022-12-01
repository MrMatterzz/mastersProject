package com.PTO.domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transport {

	private int transportID;
	private String transportType;
	private String assignedRoute;
	private String transportStatus;
	
	public Transport() {
		super();
	}
	
	public Transport(int transportID, String transportType, String assignedRoute, String transportStatus) {
		this.transportID=transportID;
		this.transportType=transportType;
		this.assignedRoute=assignedRoute;
		this.transportStatus=transportStatus;
	}
	
	public Transport(int transportID, String transportType, String transportStatus) {
		this.transportID=transportID;
		this.transportType=transportType;
		this.transportStatus=transportStatus;
	}

	public int getTransportID() {
		return transportID;
	}

	public void setTransportID(int transportID) {
		this.transportID = transportID;
	}
	
	@SuppressWarnings("exports")
	public IntegerProperty transportIDProperty() {
		return new SimpleIntegerProperty(this.getTransportID());
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	@SuppressWarnings("exports")
	public StringProperty transportTypeProperty() {
		return new SimpleStringProperty(this.getTransportType());
	}

	public String getAssignedRoute() {
		return assignedRoute;
	}

	public void setAssignedRoute(String assignedRoute) {
		this.assignedRoute = assignedRoute;
	}
	
	@SuppressWarnings("exports")
	public StringProperty assignedRouteProperty() {
		return new SimpleStringProperty(this.getAssignedRoute());
	}

	public String getTransportStatus() {
		return transportStatus;
	}
	
	public void setTransportStatus(String transportStatus) {
		this.transportStatus = transportStatus;
	}
	
	@SuppressWarnings("exports")
	public StringProperty transportStatusProperty() {
		return new SimpleStringProperty(this.getTransportStatus());
	}
}
