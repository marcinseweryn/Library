package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mysql.FirstStart;

public class StartWindowController {

	FirstStart firstStart = new FirstStart();
	
	private MainController mainControler;
	
	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}
		
	
	
    @FXML
    void initialize() {
    	/////////////////////////First Connection////////////////////////////////////// 
		FileInputStream fis;
		try {
			fis = new FileInputStream("FirstStartFile");
		} catch (FileNotFoundException e) {
			try{
			firstStart.CreateTables();
			FileOutputStream fos = new FileOutputStream("FirstStartFile");
			}catch(Exception ex){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("First launch requires a database connection!");
				alert.showAndWait();
				System.exit(0);
			}
		}

    }
		
    @FXML
    void loginAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/LoginWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void registrationAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/RegistrationWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }

	
}
