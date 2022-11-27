package com.PTO.dao;

import com.PTO.domain.RouteStop;

import javafx.collections.ObservableList;

public interface RouteStopDAO {
	
	RouteStop getStopByID(int id);
	RouteStop getStopByAddress(String address);
	ObservableList<RouteStop> getAllStops();
	boolean updateStop(RouteStop routeStop);
	boolean insertStop(RouteStop routeStop);
	boolean deleteStop(int id);
	

}
