package com.PTO.application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	//Initializing the Route objects for proper interactions;
	private RouteDAO routeDAO = new RouteDAOImpl();
	private RouteStopDAO routeStopDAO = new RouteStopDAOImpl();
	private TransportDAO transportDAO = new TransportDAOImpl();
	
	//Injecting the webView for map display
	@FXML
	private WebView routeWebView;
	
	private WebEngine engine;
	private String homepage;
	
	//private double webZoom = 1.00;
	
	//Injecting teh ChoiceBox for criteria search
	@FXML
	private ChoiceBox<String> criteriaBox, rtSrchField;
	
	private String criteriaVals[] = {"Номер маршруту", "Тип маршруту", "Номер транспорту", "Назва зупинки"};
	
	//Imjecting table and columns
	@FXML
	private TableView<Route> routesTable;
	@FXML
	private TableColumn<Route, String> routeNumber, transportType, routeStatus, firstStop, lastStop, route;
	@FXML
	private TableColumn<Route, Integer> amountOfStops;
	//Initializing observable list for table to display
	ObservableList<Route> tableData = routeDAO.getAllRoutes();
	
	//Injecting counter Labels
	@FXML
	private Label activeRoutesCounter, activeBusCounter, activeTrBusCounter, activeTramCounter;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		engine = routeWebView.getEngine();
		homepage = "https://www.google.com.ua/maps/place/%D0%9A%D0%B8%D1%97%D0%B2,+%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B0,+02000/@50.4021702,30.3926086,11z/data=!3m1!4b1!4m5!3m4!1s0x40d4cf4ee15a4505:0x764931d2170146fe!8m2!3d50.4501!4d30.5234";
		engine.load(homepage);
		
		//Populating Criteria and Search Field boxes
		criteriaBox.getItems().addAll(criteriaVals);
		criteriaBox.setValue(criteriaBox.getItems().get(0));
		criteriaBox.setOnAction(this::updateSearchBox);
		
		List<String> routeNumbers = routeDAO.getUniqueRouteIDs();
		rtSrchField.getItems().addAll(routeNumbers);
		rtSrchField.setValue(rtSrchField.getItems().get(0));
		
		initTable();
		activeRoutesCounter.setText(""+routeDAO.getRoutesByStatus("Активний").size());
		activeBusCounter.setText(""+(int) Math.floor(Math.random()*(60-40+1)+40));
		activeTrBusCounter.setText(""+(int) Math.floor(Math.random()*(60-40+1)+40));
		activeTramCounter.setText(""+(int) Math.floor(Math.random()*(60-40+1)+40));
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
	
	public void changeRouteStatus() {
		if(routesTable.getSelectionModel().getSelectedItem()!=null) {
			String status;
			if(routesTable.getSelectionModel().getSelectedItem().getRouteStatus().equals(Route.STATUS_ACTIVE)) {
				status = Route.STATUS_INACTIVE;
			} else {
				status = Route.STATUS_ACTIVE;
			}
			routesTable.getSelectionModel().getSelectedItem().setRouteStatus(status);
			routeDAO.updateRoute(routesTable.getSelectionModel().getSelectedItem());
			routesTable.refresh();
			
		} else noTableItemSelectedAlert();
	}
	
	public void openRouteDetails() throws IOException {
		if(routesTable.getSelectionModel().getSelectedItem()!=null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RouteDetails.fxml"));
			root = loader.load();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage = new Stage();
			
			RouteDetailsController routeDetailsController = loader.getController();
			Route rt = routesTable.getSelectionModel().getSelectedItem();
			routeDetailsController.initSingleRouteTable(rt);
			routeDetailsController.loadPage(rt.toRouteQuerry());
		
			Image appIcon = new Image("C:\\Users\\MISHA\\Desktop\\Work files\\MasterProject\\PTOJavaFXProject\\src\\com\\PTO\\application\\app.png");
			stage.getIcons().add(appIcon);
			stage.setResizable(false);
			stage.setTitle("Route Details");
			
			stage.setScene(scene);
			stage.show();
			
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
