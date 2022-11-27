package com.PTO.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.PTO.ConnectionFactory;
import com.PTO.domain.RouteStop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RouteStopDAOImpl implements RouteStopDAO {

	@Override
	public RouteStop getStopByID(int id) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
	             ResultSet rs = stmt.executeQuery("SELECT * FROM routeStops WHERE id=" + id);
	        		){	
			if(rs.next()){
	            	return extractRouteStopFromResultSet(rs);
	            	}
			} catch (SQLException ex) {
	            ex.printStackTrace();
	            }
		return null;
	}

	@Override
	public RouteStop getStopByAddress(String address) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
	             ResultSet rs = stmt.executeQuery("SELECT * FROM routeStops WHERE address='" + address+"'");
	        		){	
			if(rs.next()){
	            	return extractRouteStopFromResultSet(rs);
	            	}
			} catch (SQLException ex) {
	            ex.printStackTrace();
	            }
		return null;
	}

	@Override
	public ObservableList<RouteStop> getAllStops() {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM routeStops");
	        		)
	        	{ 
	          	 ObservableList<RouteStop> stops = FXCollections.observableArrayList();
		         while(rs.next()){
	                   RouteStop stop = extractRouteStopFromResultSet(rs);
	                   stops.add(stop);
	                   }
		         return stops;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public boolean updateStop(RouteStop routeStop) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("UPDATE routeStops set address = ? WHERE id=?");
	        		){
			
			ps.setString(1, routeStop.getAddress());
			ps.setInt(2, routeStop.getId());
			
			int i =ps.executeUpdate();
			
			if(i==1) return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertStop(RouteStop routeStop) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("INSERT INTO routeStops(address) VALUES(?)");
	        		){
			
			ps.setInt(1, routeStop.getId());	
			
			int i =ps.executeUpdate();
			
			if(i==1) return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteStop(int id) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("DELETE FROM routes WHERE id="+id);
	        		){
			
			int i =ps.executeUpdate();			
			if(i==1) return true;
			
		} catch(SQLException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	private RouteStop extractRouteStopFromResultSet(ResultSet rs) throws SQLException {

		RouteStop routeStop = new RouteStop();
		routeStop.setId(rs.getInt("id"));
		routeStop.setAddress( rs.getString("address") );
        
        return routeStop;
	}

}
