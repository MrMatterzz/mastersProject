package com.PTO.dao;

import java.util.List;

import com.PTO.domain.Transport;

public interface TransportDAO {
	
	Transport getTransportByID(long id);
	List<Integer> getAllTranportIDs();
	List<Transport> getAllTransport();
	List<Transport> getTransportByType(String type);
	List<Transport> getTransportByRoute(String route);
	List<Transport> getTransportByStatus(String status);
	List<Transport> getTransportByStatusAndType(String status, String type);
	boolean insertTransport(Transport transport);
	boolean updateTransport(Transport transport);
	boolean deleteTransport(long id);

}
