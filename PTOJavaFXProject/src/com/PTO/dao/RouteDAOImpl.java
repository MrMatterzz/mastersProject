package com.PTO.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.PTO.domain.Route;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.PTO.ConnectionFactory;

public class RouteDAOImpl implements RouteDAO {

	@Override
	public Route getRoutebyIDandType(String ID, String type) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
	             ResultSet rs = stmt.executeQuery("SELECT * FROM routes WHERE routeNumber=" + "'"+ID+"'" + " AND transportType="+"'"+type+"'");
	        		){	
			if(rs.next()){
	            	return extractRouteFromResultSet(rs);
	            	}
			} catch (SQLException ex) {
	            ex.printStackTrace();
	            }
		return null;
		}

	@Override
	public ObservableList<Route> getAllRoutes() {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM routes");
	        		)
	        	{ 
				 ObservableList<Route> routes = FXCollections.observableArrayList();
		         while(rs.next()){
	                   Route route = extractRouteFromResultSet(rs);
	                   routes.add(route);
	                   }
		         return routes;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public ObservableList<Route> getRoutesByType(String type) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM routes WHERE transportType="+"'"+type+"'");
	        		)
	        	{ 
				 ObservableList<Route> routes = FXCollections.observableArrayList();
		         while(rs.next()){
	                   Route route = extractRouteFromResultSet(rs);
	                   routes.add(route);
	                   }
		         return routes;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public ObservableList<Route> getRoutesByID(String ID) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM routes WHERE routeNumber="+"'"+ID+"'");
	        		)
	        	{ 
				 ObservableList<Route> routes = FXCollections.observableArrayList();
		         while(rs.next()){
	                   Route route = extractRouteFromResultSet(rs);
	                   routes.add(route);
	                   }
		         return routes;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}
	
	@Override
	public ObservableList<Route> getRoutesByStatus(String status) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM routes WHERE routeStatus="+"'"+status+"'");
	        		)
	        	{ 
				 ObservableList<Route> routes = FXCollections.observableArrayList();
		         while(rs.next()){
	                   Route route = extractRouteFromResultSet(rs);
	                   routes.add(route);
	                   }
		         return routes;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}
	
	@Override
	public List<String> getUniqueRouteIDs() {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT DISTINCT routeNumber FROM routes");
				){ 
	          	 List<String> routeNumbers = new ArrayList<String>();
		         while(rs.next()){
	                   routeNumbers.add(rs.getString("routeNumber"));
	                   }
		         return routeNumbers;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public boolean updateRoute(Route route) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("UPDATE routes set amountOfStops = ?, route = ?, routeStatus = ?, firstStop = ?, lastStop=? WHERE routeNumber=? AND transportType=?");
	        		){
			ps.setInt(1, route.getRoute().size());
			ps.setString(2, route.routeToDatabaseFormat());
			ps.setString(3, route.getRouteStatus());
			ps.setString(4, route.getFirstStop());
			ps.setString(5, route.getLastStop());
			ps.setString(6, route.getRouteNumber());
			ps.setString(7, route.getTransportType());
			
			int i =ps.executeUpdate();
			
			if(i==1) return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertRoute(Route route) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("INSERT INTO routes(routeNumber, transportType, amountOfStops, route, routeStatus, firstStop, lastStop) VALUES(?,?,?,?,?,?,?)");
	        		){
			
			ps.setString(1, route.getRouteNumber());
			ps.setString(2, route.getTransportType());
			ps.setInt(3, route.getAmountOfStops());
			ps.setString(4, route.routeToDatabaseFormat());
			ps.setString(5, route.getRouteStatus());
			ps.setString(6, route.getFirstStop());
			ps.setString(7, route.getLastStop());			
			
			int i =ps.executeUpdate();
			
			if(i==1) return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteRoute(String id, String type) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("DELETE FROM routes WHERE routeNumber='"+id+"' AND transportType='"+type+"'");
	        		){
			
			int i =ps.executeUpdate();			
			if(i==1) return true;
			
		} catch(SQLException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	private Route extractRouteFromResultSet(ResultSet rs) throws SQLException {

		Route route = new Route();
        route.setRouteNumber(rs.getString("routeNumber"));
        route.setTransportType( rs.getString("transportType") );
        route.setAmountOfStops( rs.getInt("amountOfStops") );
        route.setFirstStop( rs.getString("firstStop") );
        route.setLastStop( rs.getString("lastStop") );
        route.setRouteStatus(rs.getString("routeStatus"));
        String[] routeStr = rs.getString("route").split(", ");
        List<Integer> routeInt = new ArrayList<Integer>();
        for(int i=0;i<routeStr.length;i++) {
        	routeInt.add(Integer.parseInt(routeStr[i])); 
        }
        route.setRoute(routeInt);
        
        return route;
	}

}
