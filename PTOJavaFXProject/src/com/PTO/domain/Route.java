package com.PTO.domain;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Route {
	
	private String routeNumber;
	private String transportType;
	private int amountOfStops;
	private List<Integer> route;
	private String routeStatus;
	private String firstStop;
	private String lastStop;
	private int amountOfActiveTransport;
	
	public final static String STATUS_ACTIVE = "Активний";
	public final static String STATUS_INACTIVE = "Неактивний";
	
	public Route() {
		super();
	}
	
	public Route(String routeNumber, String transportType, int amountOfStops, List<Integer> route, String firstStop, String lastStop) {
		this.routeNumber = routeNumber;
		this.transportType = transportType;
		this.amountOfStops = amountOfStops;
		this.route = route;
		this.firstStop = firstStop;
		this.lastStop = lastStop;
		this.routeStatus = STATUS_ACTIVE;
	}
	
	public String getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(String routeNumber) {
		this.routeNumber = routeNumber;
	}
	
	public StringProperty routeNumberProperty() {
		return new SimpleStringProperty(this.getRouteNumber());
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	public StringProperty transportTypeProperty() {
		return new SimpleStringProperty(this.getTransportType());
	}

	public int getAmountOfStops() {
		return amountOfStops;
	}

	public void setAmountOfStops(int amountOfStops) {
		this.amountOfStops = amountOfStops;
	}
	
	public IntegerProperty amountOfStopsProperty() {
		return new SimpleIntegerProperty(this.getAmountOfStops());
	}

	public List<Integer> getRoute() {
		return route;
	}

	public void setRoute(List<Integer> route) {
		this.route = route;
	}
	
	public StringProperty routeProperty() {
		//System.out.println(this.getRoute().toString());
		return new SimpleStringProperty(this.getRoute().toString());
	}

	public String getFirstStop() {
		return firstStop;
	}

	public void setFirstStop(String firstStop) {
		this.firstStop = firstStop;
	}
	
	public StringProperty firstStopProperty() {
		return new SimpleStringProperty(this.getFirstStop());
	}

	public String getLastStop() {
		return lastStop;
	}

	public void setLastStop(String lastStop) {
		this.lastStop = lastStop;
	}
	
	public StringProperty lastStopProperty() {
		return new SimpleStringProperty(this.getLastStop());
	}
	
	public String getRouteStatus() {
		return routeStatus;
	}

	public void setRouteStatus(String routeStatus) {
		this.routeStatus = routeStatus;
	}
	
	public StringProperty routeStatusProperty() {
		return new SimpleStringProperty(this.getRouteStatus());
	}

	public int getAmountOfActiveTransport() {
		return amountOfActiveTransport;
	}

	public void setAmountOfActiveTransport(int amountOfActiveTransport) {
		this.amountOfActiveTransport = amountOfActiveTransport;
	}
	
	public IntegerProperty amountOfActiveTransport() {
		return new SimpleIntegerProperty(this.getAmountOfActiveTransport());
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
