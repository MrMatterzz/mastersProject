package com.PTO.dao;

import java.util.List;
import com.PTO.domain.Route;

public interface RouteDAO {
	
	Route getRoutebyIDandType(String ID, String Type);
	List<Route> getAllRoutes();
	List<Route> getRoutesByType(String type);
	List<Route> getRoutesByID(String ID);
	List<Route> getRoutesByStatus(String status);
	List<String> getUniqueRouteIDs();
	boolean updateRoute(Route route);
	boolean insertRoute(Route route);
	boolean deleteRoute(String id, String type);

}
