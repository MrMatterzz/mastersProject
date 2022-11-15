package com.PTO.dao;

import java.util.List;

import com.PTO.domain.RouteStop;

public interface RouteStopDAO {
	
	RouteStop getStopByID(int id);
	RouteStop getStopByAddress(String address);
	List<RouteStop> getAllStops();
	boolean updateStop(RouteStop routeStop);
	boolean insertStop(RouteStop routeStop);
	boolean deleteStop(int id);
	

}
