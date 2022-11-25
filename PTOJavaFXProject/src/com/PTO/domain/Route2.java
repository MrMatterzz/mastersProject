package com.PTO.domain;

import java.util.List;

public class Route2 {
	
	private String routeNumber;
	private String transportType;
	private int amountOfStops;
	private List<Integer> route;
	private String routeStatus;
	private String firstStop;
	private String lastStop;
	private int amountOfActiveTransport;
	
	public Route2() {
		super();
	}
	
	public Route2(String routeNumber, String transportType, int amountOfStops, List<Integer> route, String firstStop, String lastStop) {
		this.routeNumber = routeNumber;
		this.transportType = transportType;
		this.amountOfStops = amountOfStops;
		this.route = route;
		this.firstStop = firstStop;
		this.lastStop = lastStop;
		this.routeStatus = "�� �������";
	}
	
	public String getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(String routeNumber) {
		this.routeNumber = routeNumber;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public int getAmountOfStops() {
		return amountOfStops;
	}

	public void setAmountOfStops(int amountOfStops) {
		this.amountOfStops = amountOfStops;
	}

	public List<Integer> getRoute() {
		return route;
	}

	public void setRoute(List<Integer> route) {
		this.route = route;
	}

	public String getFirstStop() {
		return firstStop;
	}

	public void setFirstStop(String firstStop) {
		this.firstStop = firstStop;
	}

	public String getLastStop() {
		return lastStop;
	}

	public void setLastStop(String lastStop) {
		this.lastStop = lastStop;
	}
	
	public String getRouteStatus() {
		return routeStatus;
	}

	public void setRouteStatus(String routeStatus) {
		this.routeStatus = routeStatus;
	}

	public int getAmountOfActiveTransport() {
		return amountOfActiveTransport;
	}

	public void setAmountOfActiveTransport(int amountOfActiveTransport) {
		this.amountOfActiveTransport = amountOfActiveTransport;
	}
	
	public void extendRoute(int stopID) {
		this.route.add(this.route.get(route.size()-1));
		this.route.set(route.size()-2, stopID);
	}
	
	public void removeRouteStop(int stopID) {
		this.route.remove(stopID);
	}

	public String routeToDatabaseFormat(){
		String route="";
		for(int i=0;i<this.route.size();i++) {
			if(i!=this.route.size()-1)
				route+=this.route.get(i)+", ";
			else 
				route+=this.route.get(i);
		}
		return route;
	}
	
	public String toRouteQuerry() {
		String[] origin = this.firstStop.split(" ");
		String[] destination = this.lastStop.split(" ");
		String querry ="https://www.google.com/maps/dir/?api=1&origin=";
		for(int i=0;i<origin.length;i++) {
			if(i<origin.length-1)
				querry+=origin[i]+"+";
			else 
				querry+=origin[i]+"&destination=";
		}
		for(int i=0;i<destination.length;i++) {
			if(i<destination.length-1)
				querry+=destination[i]+"+";
			else 
				querry+=destination[i]+"&travelmode=transit";
		}
		return querry;
	}

}
