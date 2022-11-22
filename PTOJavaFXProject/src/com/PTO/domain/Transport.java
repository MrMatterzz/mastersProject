package com.PTO.domain;

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

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getAssignedRoute() {
		return assignedRoute;
	}

	public void setAssignedRoute(String assignedRoute) {
		this.assignedRoute = assignedRoute;
	}

	public String getTransportStatus() {
		return transportStatus;
	}

	public void setTransportStatus(String transportStatus) {
		this.transportStatus = transportStatus;
	}
	
}
