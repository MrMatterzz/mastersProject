package com.PTO.application;

import java.net.URL;
import java.time.LocalTime;
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
	
	@FXML
	private Label activeTransportCounter, reserveTransportCounter, intervalLbl;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		engine = stopWebView.getEngine();
		int x = (int) Math.floor(Math.random()*(10-2+1)+2);
		activeTransportCounter.setText(""+x);
		reserveTransportCounter.setText(""+(x+3));
		intervalLbl.setText("12хв");
	}
	
	//To be called from the MainController class. Uses the route selected in the main window to populate the info in this window and fill out the stopTable.
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
	
	//Can be called only after the initSingleRouteTable was called. Populates the stopTable based on the route provided from the MainController
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
	
	//Basically the same method as in the MainController
	public void loadPage() {
		if(stopsTable.getSelectionModel().getSelectedItem()!=null) {
			String page = stopsTable.getSelectionModel().getSelectedItem().toSearchQuerry();
			engine.load(page);
		} else noTableItemSelectedAlert();
	}
	
	//To be called from MainController, loads up the route chosen in the main window upon initialization
	public void loadPage(String page) {
		engine.load(page);
	}
	
	public void addTransport(){
		
		if(Integer.parseInt(reserveTransportCounter.getText())!=0) {
			
			int transp = Integer.parseInt(activeTransportCounter.getText())+1;
			activeTransportCounter.setText(""+transp);
			
			transp = Integer.parseInt(reserveTransportCounter.getText())-1;
			reserveTransportCounter.setText(transp+"");
			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Недостатьня кількість транспорту");
			alert.setHeaderText("В резерві не залишилося доступного транспорту!");
			alert.setContentText("Неможливо вислати більше транспорту на маршрут через відсутність доступних резервів!");
			alert.show();
		}
	}
	
	public void removeTransport() {
		
		if(Integer.parseInt(activeTransportCounter.getText())!=0) {
		int transp = Integer.parseInt(activeTransportCounter.getText())-1;
		activeTransportCounter.setText(""+transp);
		
		transp = Integer.parseInt(reserveTransportCounter.getText())+1;
		reserveTransportCounter.setText(transp+"");
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Недостатьня кількість транспорту");
			alert.setHeaderText("На маршруті не залишилось активного транспорту!");
			alert.setContentText("Неможливо відкликати транспорт з маршрут через відсутність активного транспорту на маршруті!");
			alert.show();
		}
	}
	
	//Analysis of the current load and adjustment of the route
	public void performAnalysis() {
		
		int currentHour = LocalTime.now().getHour();
		int optimalNumber=0;
		int currentActiveTransport = Integer.parseInt(activeTransportCounter.getText());
		int availableReserve = Integer.parseInt(reserveTransportCounter.getText());
		int newInterval = 0;
		if(currentHour >= 6.5 && currentHour <= 12) {
			if(currentActiveTransport<6 && availableReserve >=6) {
				optimalNumber = 7;
				availableReserve -= (optimalNumber-currentActiveTransport);
				newInterval = 5;
				transportRaised(optimalNumber, newInterval);
			}
			else if(currentActiveTransport>7) {
				optimalNumber = 7;
				availableReserve += (currentActiveTransport-optimalNumber);
				newInterval = 5;
				transportLowered(optimalNumber,newInterval);
			}
		}
		else if(currentHour>12 && currentHour<18) {
			if(currentActiveTransport<3 && availableReserve >=4) {
				optimalNumber = 5;
				availableReserve -= (optimalNumber-currentActiveTransport);
				newInterval = 13;
				transportRaised(optimalNumber, newInterval);
			}
			else if(currentActiveTransport>5) {
				optimalNumber = 5;
				availableReserve += (currentActiveTransport-optimalNumber);
				newInterval = 14;
				transportLowered(optimalNumber,newInterval);
			}
		}
		else if(currentHour>=18) {
			if(currentActiveTransport<6 && availableReserve >=6) {
				optimalNumber = 7;
				availableReserve -= (optimalNumber-currentActiveTransport);
				newInterval = 7;
				transportRaised(optimalNumber, newInterval);
			}
			else if(currentActiveTransport>7) {
				optimalNumber = 7;
				availableReserve += (currentActiveTransport-optimalNumber);
				newInterval = 8;
				transportLowered(optimalNumber,newInterval);
			}
		}
		activeTransportCounter.setText(""+optimalNumber);
		reserveTransportCounter.setText(""+availableReserve);
		intervalLbl.setText(newInterval+"хв");
	}
	//Closes current window
	public void goToMain(ActionEvent event) {
		Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	public void noTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Помилка вибору зупинки");
		alert.setHeaderText("Жодної зупинки не було обрано!");
		alert.setContentText("Будь-ласка оберіть зупинку з таблиці і повторіть операцію");
		alert.show();
	}
	
	//Below are two methods that are called after the analysis to inform the user about the changes made.
	public void transportRaised(int optimalNumber, int newInterval) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Оновлення маршруту");
		alert.setHeaderText("Кількість транспорту на маршруті та інтервал були змінені");
		alert.setContentText("Нова кількість активного транспорту була збільшена до: "+optimalNumber+"; Новий інтервал "+ newInterval+"хв.");
		alert.show();
	}
	
	public void transportLowered(int optimalNumber, int newInterval) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Оновлення маршруту");
		alert.setHeaderText("Кількість транспорту на маршруті та інтервал були змінені");
		alert.setContentText("Нова кількість активного транспорту була зменшена до: "+optimalNumber+"; Новий інтервал "+ newInterval+"хв.");
		alert.show();
	}
}