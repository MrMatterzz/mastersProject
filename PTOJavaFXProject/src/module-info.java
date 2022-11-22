module PTOJavaFXProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.web;
	requires java.sql;
	requires mysql.connector.java;
	requires javafx.base;
	
	opens com.PTO.application to javafx.graphics, javafx.fxml;
	opens com.PTO to javafx.base, javafx.fxml;
	opens com.PTO.domain to javafx.base, javafx.fxml;
	
	exports com.PTO;
	exports com.PTO.domain;
}
