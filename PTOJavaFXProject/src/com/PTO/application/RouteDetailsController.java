package com.PTO.application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.PTO.dao.RouteDAO;
import com.PTO.dao.RouteDAOImpl;
import com.PTO.dao.RouteStopDAO;
import com.PTO.dao.RouteStopDAOImpl;
import com.PTO.domain.Route;
import com.PTO.domain.RouteStop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class RouteDetailsController implements Initializable{
	
	private RouteDAO routeDAO = new RouteDAOImpl();
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
	private TableView<RouteStop> stopsTable;
	@FXML
	private TableColumn<RouteStop,Integer> stopID;
	@FXML
	private TableColumn<RouteStop,String> stopAddress;
	
	ObservableList<Route> routeTableData = FXCollections.observableArrayList();
	ObservableList<RouteStop> routeStopTableData = FXCollections.observableArrayList();

	@FXML
	private WebView stopWebView;
	private WebEngine engine;
	private String homepage;
	
	@FXML
	private Label activeTransportCounter, reserveTransportCounter;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		engine = stopWebView.getEngine();
		homepage = "https://www.google.com.ua/maps/place/%D0%9A%D0%B8%D1%97%D0%B2,+%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B0,+02000/@50.4021702,30.3926086,11z/data=!3m1!4b1!4m5!3m4!1s0x40d4cf4ee15a4505:0x764931d2170146fe!8m2!3d50.4501!4d30.5234";
		engine.load(homepage);
		
	}
	
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
	
	public void loadPage() {
		//TODO 
		if(stopsTable.getSelectionModel().getSelectedItem()!=null) {
		String page = stopsTable.getSelectionModel().getSelectedItem().toSearchQuerry();
		engine.load(page);
		} else noTableItemSelectedAlert();
	}
	
	public void loadPage(String page) {
		engine.load(page);
	}
	
	public void addTransport(){
		//TODO
	}
	
	public void removeTransport() {
		//TODO
	}
	
	public void performAnalysis() {
		//TODO
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
