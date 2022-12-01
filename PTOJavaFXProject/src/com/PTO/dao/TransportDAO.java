package com.PTO.dao;

import java.util.List;

import com.PTO.domain.Transport;

import javafx.collections.ObservableList;

public interface TransportDAO {
	
	Transport getTransportByID(long id);
	List<Integer> getAllTranportIDs();
	List<String> getAllTransportTypes();
	ObservableList<Transport> getAllTransport();
	ObservableList<Transport> getTransportByType(String type);
	ObservableList<Transport> getTransportByRoute(String route);
	ObservableList<Transport> getTransportByStatus(String status);
	ObservableList<Transport> getTransportByStatusAndType(String status, String type);
	boolean insertTransport(Transport transport);
	boolean updateTransport(Transport transport);
	boolean deleteTransport(long id);

}
