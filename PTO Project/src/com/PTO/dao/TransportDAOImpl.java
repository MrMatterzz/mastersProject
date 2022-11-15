package com.PTO.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.PTO.ConnectionFactory;
import com.PTO.domain.Transport;

public class TransportDAOImpl implements TransportDAO {

	@Override
	public Transport getTransportByID(long id) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
	             ResultSet rs = stmt.executeQuery("SELECT * FROM transport WHERE transportID=" + id);
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
	public List<Integer> getAllTranportIDs() {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
	             ResultSet rs = stmt.executeQuery("SELECT transportID FROM transport");
	        		){	
			List<Integer> transportIDs = new ArrayList<Integer>();
			while(rs.next()){
	        	 Integer transportID = rs.getInt("transportID");
	        	 transportIDs.add(transportID);
                  }
			return transportIDs;
			} catch (SQLException ex) {
	            ex.printStackTrace();
	            }
		return null;
	}

	@Override
	public List<Transport> getAllTransport() {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM transport");
	        		)
	        	{ 
	          	 List<Transport> vehicles = new ArrayList<Transport>();
		         while(rs.next()){
		        	 Transport transport = extractRouteFromResultSet(rs);
		        	 vehicles.add(transport);
	                   }
		         return vehicles;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public List<Transport> getTransportByType(String type) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM transport WHERE transportType='"+type+"'");
	        		)
	        	{ 
	          	 List<Transport> vehicles = new ArrayList<Transport>();
		         while(rs.next()){
		        	 Transport transport = extractRouteFromResultSet(rs);
		        	 vehicles.add(transport);
	                   }
		         return vehicles;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public List<Transport> getTransportByRoute(String route) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM transport WHERE assignedRoute='"+route+"'");
	        		)
	        	{ 
	          	 List<Transport> vehicles = new ArrayList<Transport>();
		         while(rs.next()){
		        	 Transport transport = extractRouteFromResultSet(rs);
		        	 vehicles.add(transport);
	                   }
		         return vehicles;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public List<Transport> getTransportByStatus(String status) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM transport WHERE transportStatus='"+status+"'");
	        		)
	        	{ 
	          	 List<Transport> vehicles = new ArrayList<Transport>();
		         while(rs.next()){
		        	 Transport transport = extractRouteFromResultSet(rs);
		        	 vehicles.add(transport);
	                   }
		         return vehicles;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public List<Transport> getTransportByStatusAndType(String status, String type) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 Statement stmt = connection.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM transport WHERE transportStatus=+"+status+"' AND transportType='"+type+"'");
	        		)
	        	{ 
	          	 List<Transport> vehicles = new ArrayList<Transport>();
		         while(rs.next()){
		        	 Transport transport = extractRouteFromResultSet(rs);
		        	 vehicles.add(transport);
	                   }
		         return vehicles;
		         } catch (SQLException ex) {
		            ex.printStackTrace();
		            }
		return null;
	}

	@Override
	public boolean insertTransport(Transport transport) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("INSERT INTO transport(transportID, transportType, assignedRoute, transportStatus) VALUES(?,?,?,?)");
	        		){
			
			ps.setInt(1, transport.getTransportID());
			ps.setString(2, transport.getTransportType());
			ps.setString(3, transport.getAssignedRoute());
			ps.setString(4, transport.getTransportStatus());			
			
			int i =ps.executeUpdate();
			
			if(i==1) return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateTransport(Transport transport) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("UPDATE transport set transportType = ?, assignedRoute = ?, transportStatus = ? WHERE transportID=?");
	        		){
			
			ps.setString(1, transport.getTransportType());
			ps.setString(2, transport.getAssignedRoute());
			ps.setString(3, transport.getTransportStatus());
			ps.setInt(4, transport.getTransportID());
			
			int i =ps.executeUpdate();
			
			if(i==1) return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteTransport(long id) {
		try (Connection connection = ConnectionFactory.getConnection();
	        	 PreparedStatement ps = connection.prepareStatement("DELETE FROM transport WHERE transportID="+id);
	        		){
			
			int i =ps.executeUpdate();			
			if(i==1) return true;
			
		} catch(SQLException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	private Transport extractRouteFromResultSet(ResultSet rs) throws SQLException {

		Transport transport = new Transport();
        transport.setTransportID(rs.getInt("transportID"));
        transport.setTransportType( rs.getString("transportType") );
        transport.setAssignedRoute( rs.getString("assignedRoute") );
        transport.setTransportStatus( rs.getString("TransportStatus") );
        
        return transport;
	}

}
