package com.PTO.application;

import java.util.List;

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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class RouteEditController{

private RouteStopDAO routeStopDAO = new RouteStopDAOImpl();
private RouteDAO routeDAO = new RouteDAOImpl();
	
	//Injecting the table and rows for the route
	@FXML
	private TableView<Route> singleRouteTable;
	@FXML
	private TableColumn<Route, String> routeNumber, transportType, routeStatus, firstStop, lastStop, route;
	@FXML
	private TableColumn<Route, Integer> amountOfStops;
	
	//Injecting table and columns for both route stops instances of tables
	@FXML
	private TableView<RouteStop> stopsTable, actionStopsTable;
	@FXML
	private TableColumn<RouteStop,Integer> stopID, actionStopID;
	@FXML
	private TableColumn<RouteStop,String> stopAddress, actionStopAddress;
	
	@FXML
	private Button addStopBtn, removeStopBtn, changeStopBtn;
	
	ObservableList<Route> routeTableData = FXCollections.observableArrayList();
	ObservableList<RouteStop> routeStopTableData = FXCollections.observableArrayList();
	ObservableList<RouteStop> actionRouteStopTableData = FXCollections.observableArrayList();
	
	private int stopIndexForChange;
	
	//The same method as in the RouteDetailsController
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
	
	//The same method as in the RouteDetailsController
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
	
	//Populates the table on the right for stop addition and changing. Calls all stops but removes the ones that are already in the route.
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
	
	//On first call disables the other two buttons and calls initActionRouteStopTable
	//On the second call takes the stop that user chose in the table on the right and adds it before the last stop in the route.
	//After action is done, re-enables other buttons, clears the action table and refreshes the stopsTable and singleRouteTable.
	public void addStop() {
		if (!actionRouteStopTableData.isEmpty()) {
			if(actionStopsTable.getSelectionModel().getSelectedItem()!=null) {
				RouteStop stop = actionStopsTable.getSelectionModel().getSelectedItem();
				routeTableData.get(0).extendRoute(stop.getId());
				routeTableData.get(0).setAmountOfStops(routeTableData.get(0).getRoute().size());
				routeDAO.updateRoute(routeTableData.get(0));
				
				singleRouteTable.refresh();
				routeStopTableData.clear();
				initRouteStopTable();
				
				actionRouteStopTableData.clear();
				actionStopsTable.refresh();
				removeStopBtn.setDisable(false);
				changeStopBtn.setDisable(false);
			}
			else noTableItemSelectedAlert();
			
		}
		else {
			initActionRouteStopTable();
			removeStopBtn.setDisable(true);
			changeStopBtn.setDisable(true);
		}
	}
	
	//On first call disables the other two buttons and calls initActionRouteStopTable. Requires user to chose the stop he wants to replace first.
	//On the second call takes the stop that user chose in the table on the right and sets it instead of the one he chose on the left.
	//After action is done, re-enables other buttons, clears the action table and refreshes the stopsTable and singleRouteTable.
	public void changeStop() {
		
			if (!actionRouteStopTableData.isEmpty()) {
				if(actionStopsTable.getSelectionModel().getSelectedItem()!= null && stopsTable.getSelectionModel().getSelectedItem() != null) {
					Route rt = routeTableData.get(0);
					routeStopTableData.set(stopIndexForChange, actionStopsTable.getSelectionModel().getSelectedItem());
					rt.getRoute().set(stopIndexForChange, actionStopsTable.getSelectionModel().getSelectedItem().getId());
					routeDAO.updateRoute(rt);
					routeTableData.set(0, rt);
					
					singleRouteTable.refresh();
					stopsTable.refresh();
					actionRouteStopTableData.clear();
					actionStopsTable.refresh();
					addStopBtn.setDisable(false);
					removeStopBtn.setDisable(false);
				}
				else noTableItemSelectedAlert();
				
			}
			else {
				if(stopsTable.getSelectionModel().getSelectedItem() != null) {
					stopIndexForChange = stopsTable.getSelectionModel().getSelectedIndex();
					initActionRouteStopTable();
					addStopBtn.setDisable(true);
					removeStopBtn.setDisable(true);
				} else noTableItemSelectedAlert();
			}
	}
	
	//Just removes the stop the user chose from the route. Nothing fancy
	public void removeStop() {
		if(stopsTable.getSelectionModel().getSelectedItem()!=null) {
			if(stopsTable.getSelectionModel().getSelectedItem()==routeStopTableData.get(0) || stopsTable.getSelectionModel().getSelectedItem()==routeStopTableData.get(routeStopTableData.size()-1)) {
				incorrectTableItemSelectedAlert();
			} else {
				if(confirmationAlert() == ButtonType.OK) {
					int stopId = stopsTable.getSelectionModel().getSelectedIndex();
					routeTableData.get(0).removeRouteStop(stopId);
					routeTableData.get(0).setAmountOfStops(routeTableData.get(0).getRoute().size());
					routeDAO.updateRoute(routeTableData.get(0));
					
					singleRouteTable.refresh();
					routeStopTableData.remove(stopsTable.getSelectionModel().getSelectedItem());
					stopsTable.refresh();
				}
			}
		}
		else {
			noTableItemSelectedAlert();
		}
	}
	
	//Closes current window
	public void goToMain(ActionEvent event) {
		Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	public void noTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Помилка вибору зупинки!");
		alert.setHeaderText("Жодної зупинки не було вибрано!");
		alert.setContentText("Будь-ласка оберіть зупинку із таблиці та повторіть операцію.");
		alert.showAndWait();
	}
	
	public void incorrectTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Помилка вибору зупинки!");
		alert.setHeaderText("Неможливо змінити або видалити першу та останню зупинки!");
		alert.setContentText("Будь-ласка оберіть іншу зупинку у таблиці та повторіть операцію.");
		alert.showAndWait();
	}
	
	public ButtonType confirmationAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Прибираня зупинки");
		alert.setHeaderText("Підтвердіть видалення зупинки з маршруту");
		alert.setContentText("Ви впевнені що хочете прибрати цю зупинку?");
		return alert.showAndWait().get();
	}
	
	//in case user wants to cancel the chosen action. Re-enables all the buttons and clears the actionStopsTable
	public void cancelAction() {
		addStopBtn.setDisable(false);
		removeStopBtn.setDisable(false);
		changeStopBtn.setDisable(false);
		actionRouteStopTableData.clear();
		actionStopsTable.refresh();
	}
}
