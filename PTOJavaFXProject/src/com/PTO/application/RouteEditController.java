package com.PTO.application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
		
	}
	
	public void addStop() {
		//TODO
	}
	
	public void changeStop() {
		
	}
	
	public void removeStop() {
		
	}
	
	public void goToMain(ActionEvent event) {
		Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	public void noTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Route Selection Error");
		alert.setHeaderText("No stop is chosen for display!");
		alert.setContentText("Please chose a stop in the table and repeat the operation");
		alert.show();
	}
}
