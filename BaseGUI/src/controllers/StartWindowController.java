package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mysql.FirstStart;

public class StartWindowController {

	FirstStart firstStart = new FirstStart();
	
	private MainController mainControler;
	
    @FXML
    private JFXButton loginButton, registrationButton;
	
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
    void loginMouseEntered(MouseEvent event) {
    	loginButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void loginMouseExited(MouseEvent event) {
    	loginButton.setStyle("-fx-background-color:  #2196f3");
    }

    @FXML
    void registrationAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/RegistrationWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void registrationMouseEntered(MouseEvent event) {
    	registrationButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void registrationMouseExited(MouseEvent event) {
    	registrationButton.setStyle("-fx-background-color:  #2196f3");
    }

	
}
