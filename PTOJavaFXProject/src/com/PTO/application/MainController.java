package com.PTO.application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class MainController implements Initializable{
	
	private RouteDAO routeDAO = new RouteDAOImpl();
	private RouteStopDAO routeStopDAO = new RouteStopDAOImpl();
	private TransportDAO transportDAO = new TransportDAOImpl();
	
	@FXML
	private WebView routeWebView;
	
	private WebEngine engine;
	private String homepage;
	
	private double webZoom = 1.00;
	
	@FXML
	private ChoiceBox<String> criteriaBox, rtSrchField;
	
	private String criteriaVals[] = {"Номер маршруту", "Тип маршруту", "Номер транспорту", "Назва зупинки"};
	
	@FXML
	private TableView<Route> routesTable;
	
	@FXML
	private TableColumn<Route, String> routeNumber, transportType, routeStatus, firstStop, lastStop;
	@FXML
	private TableColumn<Route, Integer> amountOfStops;
	@FXML
	private TableColumn<Route, List<Integer>> route;
	
	ObservableList<Route> tableData = FXCollections.observableArrayList(routeDAO.getAllRoutes());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		engine = routeWebView.getEngine();
		homepage = "https://www.google.com/maps/place/%D0%9A%D0%B8%D1%97%D0%B2,+%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B0,+02000/@50.4021702,30.3926086,11z/data=!3m1!4b1!4m5!3m4!1s0x40d4cf4ee15a4505:0x764931d2170146fe!8m2!3d50.4501!4d30.5234";
		loadPage(homepage);
		
		//Populating Criteria and Search Field boxes
		criteriaBox.getItems().addAll(criteriaVals);
		criteriaBox.setValue(criteriaBox.getItems().get(0));
		criteriaBox.setOnAction(this::updateSearchBox);
		
		List<String> routeNumbers = routeDAO.getUniqueRouteIDs();
		rtSrchField.getItems().addAll(routeNumbers);
		rtSrchField.setValue(rtSrchField.getItems().get(0));
		
		//Populating the Routes Table
		routeNumber.setCellValueFactory(new PropertyValueFactory<>("RouteNumber"));
		transportType.setCellValueFactory(new PropertyValueFactory<>("TransportType"));
		amountOfStops.setCellValueFactory(new PropertyValueFactory<>("AmountOfStops"));
		route.setCellValueFactory(new PropertyValueFactory<>("Route"));
		routeStatus.setCellValueFactory(new PropertyValueFactory<>("RouteStatus"));
		firstStop.setCellValueFactory(new PropertyValueFactory<>("FirstStop"));
		lastStop.setCellValueFactory(new PropertyValueFactory<>("LastStop"));
		routesTable.setItems(tableData);
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
	
	//WebView Control Methods
	public void loadPage(String page) {
		engine.load(page);
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
}
