package com.PTO.application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,1440,900);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Image appIcon = new Image("C:\\Users\\MISHA\\Desktop\\Work files\\MasterProject\\PTOJavaFXProject\\src\\com\\PTO\\application\\app.png");
			primaryStage.getIcons().add(appIcon);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Public Traffic Organizer");
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(event ->{
				event.consume();
				logout(primaryStage);
				});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
public void logout(Stage stage) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You are about to loguot");
		alert.setContentText("Are you sure you want to quit");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			stage.close();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
