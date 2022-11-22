package com.PTO.dao;

import java.util.List;
import com.PTO.domain.Route;

import javafx.collections.ObservableList;

public interface RouteDAO {
	
	Route getRoutebyIDandType(String ID, String Type);
	ObservableList<Route> getAllRoutes();
	ObservableList<Route> getRoutesByType(String type);
	ObservableList<Route> getRoutesByID(String ID);
	ObservableList<Route> getRoutesByStatus(String status);
	List<String> getUniqueRouteIDs();
	boolean updateRoute(Route route);
	boolean insertRoute(Route route);
	boolean deleteRoute(String id, String type);

}
