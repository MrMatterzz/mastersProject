package com.PTO.domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RouteStop {
	
	private int id;
	private String address;
	
	public RouteStop() {
		super();
	}
	
	public RouteStop(int id, String address) {
		this.id = id;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public IntegerProperty idProperty() {
		return new SimpleIntegerProperty(this.getId());
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public StringProperty addressProperty() {
		return new SimpleStringProperty(this.getAddress());
	}
	
	public String toSearchQuerry() {
		String[] stopAddress = this.address.split(" ");
		String querry = "https://www.google.com/maps/search/?api=1&query=�������+";
		for(int i=0;i<stopAddress.length;i++) {
			if(i<stopAddress.length-1)
				querry+=stopAddress[i]+"+";
			else 
				querry+=stopAddress[i]+"&,+���";
		} 
		return querry;
	}
	
	@Override
	public String toString() {
		String routeStop = this.getId() + " " + this.getAddress();
		return routeStop;
	}
}
