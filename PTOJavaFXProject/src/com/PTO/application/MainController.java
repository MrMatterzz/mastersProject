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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
	
	private Image appIcon = new Image(getClass().getResource("/com/PTO/application/app.png").toString());
	
	private final String adminPass = "adminPass";
	
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
	
	//Injecting table and columns
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
	
	//Table is being updated based on the criteria in the criteria box and srchBox. Info is being pulled from the database on each request so it always up to date.  
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
	
	//Reloads the routeWebView module. Not actually used anywhere though.
	public void refreshPage() {
		engine.reload();
	}
	
	//Takes the selected route from the table and changes it's status to opposite. Posts changes to the database and updates the table.
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
	
	//Opens a new Window with the Specified route details; Sends the chosen route to that window's controller to populate local tables.
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
		
			stage.getIcons().add(appIcon);
			stage.setResizable(false);
			stage.setTitle("Route Details");
			
			stage.setScene(scene);
			stage.show();
			
		} else noTableItemSelectedAlert();
	}
	
	//To be called when trying to perform an action that requires user to chose an item from the table;
	public void noTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Помилка");
		alert.setHeaderText("Жодного маршруту не було обрано для відображення!");
		alert.setContentText("Будь-ласка оберіть маршрут з таблиці і повторіть операцію");
		alert.showAndWait();
	}
	
	//Creates a window with the password field. To be called when performing an action that requires authorization
	//e.g. changing route status or editing route.
	public void authWindow(ActionEvent event) throws IOException {
		if(routesTable.getSelectionModel().getSelectedItem()!=null) {
			Button callerBtn = (Button) event.getSource();
			//System.out.println(callerBtn.getId());
			stage = new Stage();
			stage.getIcons().add(appIcon);
			stage.setResizable(false);
			stage.setTitle("Authentication");
			PasswordField ps = new PasswordField();
			ps.setPrefWidth(200);
			Button loginBtn = new Button("Увійти");
			loginBtn.setOnAction(e -> {
				try {
					login(e, ps.getText(), callerBtn.getId());
				} catch (IOException err) {
					err.printStackTrace();
				}
			});
			HBox hb = new HBox(ps,loginBtn);
			scene = new Scene(hb,250,30);
			stage.setScene(scene);
			stage.show();
		} else noTableItemSelectedAlert();
	}
	
	//Being called from the authWindow to verify the password provided. 
	//Takes event, String password and string callerBtnId as arguments. event is used to close the authWindow itself. 
	//password is being verified to authenticate the action. and callerBtnId is used to determine what action has to be performed.
	public void login(ActionEvent event, String password, String callerBtnId) throws IOException {
//		Button button = (Button) event.getSource();
//		System.out.println(button.getId());
			stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			if(password.equals(adminPass)) {
				if(callerBtnId.equals("routeEditBtn")) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("RouteEdit.fxml"));
					root = loader.load();
					scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					stage = new Stage();
					
					RouteEditController routeEditController = loader.getController();
					Route rt = routesTable.getSelectionModel().getSelectedItem();
					routeEditController.initSingleRouteTable(rt);
					
					stage.getIcons().add(appIcon);
					stage.setResizable(false);
					stage.setTitle("Route Editing");
					
					stage.setScene(scene);
					stage.show();
				} else changeRouteStatus();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Помилка авторизації");
				alert.setHeaderText("Помилка авторизації");
				alert.setContentText("Ви ввели некоректний код доступу, повторіть операцію і спробуйте інший код");
				alert.showAndWait();
			}
	}
}
