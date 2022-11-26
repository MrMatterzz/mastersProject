package com.PTO.application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import com.PTO.dao.RouteDAO;
import com.PTO.dao.RouteDAOImpl;
import com.PTO.dao.RouteStopDAO;
import com.PTO.dao.RouteStopDAOImpl;
import com.PTO.dao.TransportDAO;
import com.PTO.dao.TransportDAOImpl;
import com.PTO.domain.Route;
import com.PTO.domain.RouteStop;
import com.PTO.domain.Transport;

public class MainController implements Initializable{
	
	//Initializing the Route objects for proper interactions;
	private RouteDAO routeDAO = new RouteDAOImpl();
	private RouteStopDAO routeStopDAO = new RouteStopDAOImpl();
	private TransportDAO transportDAO = new TransportDAOImpl();
	
	//Injecting the webView for map display
	@FXML
	private WebView routeWebView;
	
	private WebEngine engine;
	private String homepage;
	
	private double webZoom = 1.00;
	
	//Injecting teh ChoiceBox for criteria search
	@FXML
	private ChoiceBox<String> criteriaBox, rtSrchField;
	
	private String criteriaVals[] = {"Номер маршруту", "Тип маршруту", "Номер транспорту", "Назва зупинки"};
	
	//Imjecting table and columns
	@FXML
	private TableView<Route> routesTable;
	@FXML
	private TableColumn<Route, String> routeNumber, transportType, routeStatus, firstStop, lastStop;
	@FXML
	private TableColumn<Route, Integer> amountOfStops;
	@FXML
	private TableColumn<Route, String> route;
	//Initializing observable list for table to display
	ObservableList<Route> tableData = routeDAO.getAllRoutes();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		engine = routeWebView.getEngine();
		homepage = "https://www.google.com/maps/place/%D0%9A%D0%B8%D1%97%D0%B2,+%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B0,+02000/@50.4021702,30.3926086,11z/data=!3m1!4b1!4m5!3m4!1s0x40d4cf4ee15a4505:0x764931d2170146fe!8m2!3d50.4501!4d30.5234";
		engine.load(homepage);
		
		//Populating Criteria and Search Field boxes
		criteriaBox.getItems().addAll(criteriaVals);
		criteriaBox.setValue(criteriaBox.getItems().get(0));
		criteriaBox.setOnAction(this::updateSearchBox);
		
		List<String> routeNumbers = routeDAO.getUniqueRouteIDs();
		rtSrchField.getItems().addAll(routeNumbers);
		rtSrchField.setValue(rtSrchField.getItems().get(0));
		
		initTable();
	}
	
	//Choice box manipulation method. Repopulates the rtSrchField based on the current selected value in criteriaBox.
	public void updateSearchBox(ActionEvent event) {
		String currentCriteria = criteriaBox.getValue();
		rtSrchField.getItems().clear();
		
		switch(currentCriteria) {
		
		case ("Номер маршруту"):
			List<String> routeNumbers = routeDAO.getUniqueRouteIDs();
			rtSrchField.getItems().addAll(routeNumbers);
			rtSrchField.setValue(rtSrchField.getItems().get(0));
			break;
		
		case ("Тип маршруту"):
			rtSrchField.getItems().addAll("Автобус","Тролейбус","Трамвай");
			rtSrchField.setValue(rtSrchField.getItems().get(0));
			break;
		
		case("Номер транспорту"):
			List<Integer> transportIDs = transportDAO.getAllTranportIDs();
			for(Integer id : transportIDs) {
				rtSrchField.getItems().add(id.toString());
				rtSrchField.setValue(rtSrchField.getItems().get(0));
			}
			break;
		
		case("Назва зупинки"):
			List<RouteStop> routeStops = routeStopDAO.getAllStops();
			for(RouteStop routeStop : routeStops) {
				rtSrchField.getItems().add(routeStop.toString());
				rtSrchField.setValue(rtSrchField.getItems().get(0));
			}
			break;
		}
	}
	
	//Initializing and Populating the Routes Table
	public void initTable() {
		
		routeNumber.setCellValueFactory(data->data.getValue().routeNumberProperty());
		transportType.setCellValueFactory(data->data.getValue().transportTypeProperty());
		amountOfStops.setCellValueFactory(data->data.getValue().amountOfStopsProperty().asObject());
		route.setCellValueFactory(data->data.getValue().routeProperty());
		routeStatus.setCellValueFactory(data->data.getValue().routeStatusProperty());
		firstStop.setCellValueFactory(data->data.getValue().firstStopProperty());
		lastStop.setCellValueFactory(data->data.getValue().lastStopProperty());
		routesTable.setItems(tableData);
		
	}
	
	public void updateTableData(ActionEvent event) {
		
		String criteria = criteriaBox.getValue();
		
		switch(criteria) {
		
			case ("Номер маршруту"):
				tableData = routeDAO.getRoutesByID(rtSrchField.getValue());
				routesTable.setItems(tableData);
				break;
			
			case ("Тип маршруту"):
				tableData = routeDAO.getRoutesByType(rtSrchField.getValue());
				routesTable.setItems(tableData);
				break;
			
			case("Номер транспорту"):
				Transport transport = transportDAO.getTransportByID(Integer.parseInt(rtSrchField.getValue()));
				tableData.clear();
				tableData.add(routeDAO.getRoutebyIDandType(transport.getAssignedRoute(), transport.getTransportType()));
				routesTable.setItems(tableData);
				break;
			
			case("Назва зупинки"):
				String routeStop[] = rtSrchField.getValue().split(" ");
				int stopID = Integer.parseInt(routeStop[0]);
				ObservableList<Route> routesForCheck = routeDAO.getAllRoutes();
				tableData.clear();
				for (Route route : routesForCheck) {
					if (route.getRoute().contains(stopID)) {
						tableData.add(route);
					}
				}
				routesTable.setItems(tableData);
				break;
			default:
				resetTableData();
				break;
		}
		
	}
	//Resets table data to display all routes and drop the search criteria
	public void resetTableData() {
		tableData = routeDAO.getAllRoutes();
		routesTable.setItems(tableData);
	}
	
	//WebView Control Methods
	public void loadPage(ActionEvent event) {
		if(routesTable.getSelectionModel().getSelectedItem()!=null) {
			String routeMapPage = routesTable.getSelectionModel().getSelectedItem().toRouteQuerry();
			engine.load(routeMapPage);
		}
		else {
			noTableItemSelectedAlert();
		}
		
	}
	
	public void refreshPage() {
		engine.reload();
	}
	
	public void zoomIn() {
		webZoom+=0.25;
		routeWebView.setZoom(webZoom);
	}
	
	public void zoomOut() {
		webZoom-=0.25;
		routeWebView.setZoom(webZoom);
	}
	
	public void changeRouteStatus() {
		if(routesTable.getSelectionModel().getSelectedItem()!=null) {
			String status;
			if(routesTable.getSelectionModel().getSelectedItem().getRouteStatus().equals("Активний")) {
				status = "Неактивний";
			} else {
				status = "Активний";
			}
			routesTable.getSelectionModel().getSelectedItem().setRouteStatus(status);
			routeDAO.updateRoute(routesTable.getSelectionModel().getSelectedItem());
			routesTable.refresh();
			
		} else noTableItemSelectedAlert();
	}
	
	public void noTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Route Selection Error");
		alert.setHeaderText("No route is chosen for display!");
		alert.setContentText("Please chose a route in the table and repeat the operation");
		alert.show();
	}
}
