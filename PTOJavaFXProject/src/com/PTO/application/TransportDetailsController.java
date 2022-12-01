package com.PTO.application;

import java.net.URL;
import java.util.ResourceBundle;

import com.PTO.dao.RouteDAO;
import com.PTO.dao.RouteDAOImpl;
import com.PTO.dao.TransportDAO;
import com.PTO.dao.TransportDAOImpl;
import com.PTO.domain.Transport;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;


public class TransportDetailsController implements Initializable {
	
	private TransportDAO transportDAO = new TransportDAOImpl();
	private RouteDAO routeDAO = new RouteDAOImpl();

	@FXML
	private ChoiceBox<String> transCriteriaBox, transSrchBox, newStatusBox;
	
	@FXML
	private TableView<Transport> transTable;
	
	@FXML
	private TableColumn<Transport,Integer> transIDClmn;
	
	@FXML
	private TableColumn<Transport,String> transTypeClmn, assignedRouteClmn, transStatusClmn;
	
	private String[] possibleStatuses = {"Активний", "На маршруті","В депо","На ремонті","Списаний"};
	private String[] srchCriteriaVals = {"Тип транспорту","Номер маршруту","Статус транспорту"};
	
	ObservableList<Transport> transTableData = transportDAO.getAllTransport();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		transCriteriaBox.setOnAction(this::updateSearchBox);
		transCriteriaBox.getItems().addAll(srchCriteriaVals);
		transCriteriaBox.setValue(transCriteriaBox.getItems().get(0));
		
		newStatusBox.getItems().addAll(possibleStatuses);
		newStatusBox.setValue(newStatusBox.getItems().get(0));
		
		initTransTable();
	}
	
	public void updateSearchBox(ActionEvent event) {
		
		String criteria = transCriteriaBox.getValue();
		transSrchBox.getItems().clear();
		
		switch(criteria) {
				
			case "Тип транспорту":
				transSrchBox.getItems().addAll(transportDAO.getAllTransportTypes());
				transSrchBox.setValue(transSrchBox.getItems().get(0));
				break;
					
			case "Номер маршруту":
				transSrchBox.getItems().addAll(routeDAO.getUniqueRouteIDs());
				transSrchBox.setValue(transSrchBox.getItems().get(0));
				break;
					
			case "Статус транспорту":
				transSrchBox.getItems().addAll(possibleStatuses);
				transSrchBox.setValue(transSrchBox.getItems().get(0));
				break;
		}
	}
	
	public void initTransTable() {
		
		transIDClmn.setCellValueFactory(data->data.getValue().transportIDProperty().asObject());
		transTypeClmn.setCellValueFactory(data->data.getValue().transportTypeProperty());
		assignedRouteClmn.setCellValueFactory(data->data.getValue().assignedRouteProperty());
		transStatusClmn.setCellValueFactory(data->data.getValue().transportStatusProperty());
		transTable.setItems(transTableData);
		
	}

	public void updateTransTable() {
		
		String criteria = transCriteriaBox.getValue();
		switch(criteria) {
		
		case "Тип транспорту":
			transTableData = transportDAO.getTransportByType(transSrchBox.getValue());
			transTable.setItems(transTableData);
			break;
			
		case "Номер маршруту":
			transTableData = transportDAO.getTransportByRoute(transSrchBox.getValue());
			transTable.setItems(transTableData);
			break;
			
		case "Статус транспорту":
			transTableData = transportDAO.getTransportByStatus(transSrchBox.getValue());
			transTable.setItems(transTableData);
			break;
		default:
			resetTransTable();
			break;
		}
		
	}
	
	public void resetTransTable() {
		transTableData = transportDAO.getAllTransport();
		transTable.setItems(transTableData);
	}
	
	public void submitStatus() {
		
		if(transTable.getSelectionModel().getSelectedItem()!=null) {
			
			if(confirmationAlert() == ButtonType.OK) {
				transTable.getSelectionModel().getSelectedItem().setTransportStatus(newStatusBox.getValue());
				transportDAO.updateTransport(transTable.getSelectionModel().getSelectedItem());
				transTable.refresh();
			}
			
		} else noTableItemSelectedAlert();
		
	}
	
	public void noTableItemSelectedAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Помилка вибору транспорту");
		alert.setHeaderText("Жодної машини не було обрано!");
		alert.setContentText("Будь-ласка оберіть машину з таблиці і повторіть операцію");
		alert.show();
	}
	
	public ButtonType confirmationAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Зміна статусу");
		alert.setHeaderText("Підтвердіть зміну статусу мишини");
		alert.setContentText("Ви впевнені що хочете змінити статус машини на обраний?");
		return alert.showAndWait().get();
	}
}
