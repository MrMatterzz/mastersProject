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
			
			Image appIcon = new Image(getClass().getResource("/com/PTO/application/app.png").toString());
			primaryStage.getIcons().add(appIcon);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Public Ground Traffic Automatization Assistant");
			
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
		alert.setTitle("Вихід");
		alert.setHeaderText("Ви виходите із системи");
		alert.setContentText("Ви впевнені що зочете вийти?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			stage.close();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
