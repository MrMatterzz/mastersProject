package com.PTO.application;

import java.util.List;

import com.PTO.dao.RouteStopDAO;
import com.PTO.dao.RouteStopDAOImpl;
import com.PTO.domain.Route;
import com.PTO.domain.RouteStop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RouteEditController{

private RouteStopDAO routeStopDAO = new RouteStopDAOImpl();
	
	//Injecting the table and rows for the route
	@FXML
	private TableView<Route> singleRouteTable;
	@FXML
	private TableColumn<Route, String> routeNumber, transportType, routeStatus, firstStop, lastStop, route;
	@FXML
	private TableColumn<Route, Integer> amountOfStops;
	
	//Injecting table and columns for the route stops
	@FXML
	private TableView<RouteStop> stopsTable, actionStopsTable;
	@FXML
	private TableColumn<RouteStop,Integer> stopID, actionStopID;
	@FXML
	private TableColumn<RouteStop,String> stopAddress, actionStopAddress;
	
	ObservableList<Route> routeTableData = FXCollections.observableArrayList();
	ObservableList<RouteStop> routeStopTableData = FXCollections.observableArrayList();
	ObservableList<RouteStop> actionRouteStopTableData = FXCollections.observableArrayList();
	
	public void initSingleRouteTable(Route rt) {
		routeNumber.setCellValueFactory(data->data.getValue().routeNumberProperty());
		transportType.setCellValueFactory(data->data.getValue().transportTypeProperty());
		amountOfStops.setCellValueFactory(data->data.getValue().amountOfStopsProperty().asObject());
		route.setCellValueFactory(data->data.getValue().routeProperty());
		routeStatus.setCellValueFactory(data->data.getValue().routeStatusProperty());
		firstStop.setCellValueFactory(data->data.getValue().firstStopProperty());
		lastStop.setCellValueFactory(data->data.getValue().lastStopProperty());
		routeTableData.add(rt);
		singleRouteTable.setItems(routeTableData);
		initRouteStopTable();
	}
	
	public void initRouteStopTable() {
		
		stopID.setCellValueFactory(data->data.getValue().idProperty().asObject());
		stopAddress.setCellValueFactory(data->data.getValue().addressProperty());
		
		Route rt = routeTableData.get(0);
		List<Integer> stops = rt.getRoute();
		for(int stopID : stops) {
			routeStopTableData.add(routeStopDAO.getStopByID(stopID));
		}
		
		stopsTable.setItems(routeStopTableData);
		
	}
	
	public void initActionRouteStopTable() {
		
		actionStopID.setCellValueFactory(data->data.getValue().idProperty().asObject());
		actionStopAddress.setCellValueFactory(data->data.getValue().addressProperty());
		
		actionRouteStopTableData = routeStopDAO.getAllStops();
		//System.out.println(routeStopTableData);
		for(int i=0; i<actionRouteStopTableData.size();i++) {
			for(RouteStop rs : routeStopTableData) {
				if(rs.getId()==actionRouteStopTableData.get(i).getId()) {
					actionRouteStopTableData.remove(i);
				}
			}
		}
		actionStopsTable.setItems(actionRouteStopTableData);
	}
	
	public void addStop() {
		if (!actionRouteStopTableData.isEmpty()) {
			if(actionStopsTable.getSelectionModel().getSelectedItem()!=null) {
				//TODO implement proper action for this method
				actionRouteStopTableData.clear();
				actionStopsTable.refresh();
			}
			else noTableItemSelectedAlert();
			
		}
		else {
			initActionRouteStopTable();
		}
	}
	
	public void changeStop() {
		//TODO implement proper action for this method
	}
	
	public void removeStop() {
		if(stopsTable.getSelectionModel().getSelectedItem()!=null) {
			if(stopsTable.getSelectionModel().getSelectedItem()==routeStopTableData.get(0) || stopsTable.getSelectionModel().getSelectedItem()==routeStopTableData.get(routeStopTableData.size()-1)) {
				incorrectTableItemSelectedAlert();
			} else {
				routeStopTableData.remove(stopsTable.getSelectionModel().getSelectedItem());
				stopsTable.refresh();
			}
		}
		else {
			noTableItemSelectedAlert();
		}
	}
	
	public void goToMain(ActionEvent event) {
		Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	public void noTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Stop Selection Error");
		alert.setHeaderText("No stop is chosen for display!");
		alert.setContentText("Please chose a stop in the table and repeat the operation");
		alert.showAndWait();
	}
	
	public void incorrectTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Stop Selection Error");
		alert.setHeaderText("Cannot change or remove either First or Last stop!");
		alert.setContentText("Please chose a different stop in the table and repeat the operation");
		alert.showAndWait();
	}
	
//	public void incorrectTableItemSelectedAlert2() {
//		Alert alert = new Alert(AlertType.ERROR);
//		alert.setTitle("Stop Selection Error");
//		alert.setHeaderText("Cannot add or change the stop to the one that already exists on the route!");
//		alert.setContentText("Please chose a different stop in the table and repeat the operation");
//		alert.showAndWait();
//	}
}
