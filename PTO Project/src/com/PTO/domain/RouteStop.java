package com.PTO.domain;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toSearchQuerry() {
		String[] stopAddress = this.address.split(" ");
		String querry = "https://www.google.com/maps/search/?api=1&query=зупинка+";
		for(int i=0;i<stopAddress.length;i++) {
			if(i<stopAddress.length-1)
				querry+=stopAddress[i]+"+";
			else 
				querry+=stopAddress[i]+"&,+Київ";
		} 
		return querry;
	}
	
	@Override
	public String toString() {
		String routeStop = this.getId() + " " + this.getAddress();
		return routeStop;
	}
}
