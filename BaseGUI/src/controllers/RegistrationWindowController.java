package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mysql.ConnectionToDatabase;
import javafx.scene.Node;

public class RegistrationWindowController {

    @FXML
    private JFXTextField firstNameField, lastNameField, cityField, addressField, postalCodeField,
    telephoneField, emailField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton submitButton, menuButton;

    @FXML
    void backToStartAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/StartWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void submitAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    	
    	if(firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
    			cityField.getText().isEmpty() || addressField.getText().isEmpty() ||
    			postalCodeField.getText().isEmpty() || telephoneField.getText().isEmpty() ||
    			emailField.getText().isEmpty() || passwordField.getText().isEmpty()){
    		
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("All fields must be filled!");
			alert.showAndWait();
    		
    	}else if(!postalCodeField.getText().matches("\\d{2}-\\d{3}")){
    		
    		WarningAlert("Incorrect postal code!");
    		
    	}else if(!telephoneField.getText().matches("^[0-9]{9}$")){
    		
    		WarningAlert("Incorrect phone number!");
    		
    	}else if(!firstNameField.getText().matches("^[a-zA-Z\\s]{2,35}$")){
    		
    		WarningAlert("Incorrect name!");
    		
    	}else if(!lastNameField.getText().matches("^[a-zA-Z\\s]{2,35}$")){
    		
    		WarningAlert("Incorrect surname!");
    		
    	}else if(!emailField.getText().matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$")){
    		
    		WarningAlert("Incorrect email!");
    	
    	}else if(!cityField.getText().matches("^[a-zA-Z\\s]{2,45}$")){
    		
    		WarningAlert("Incorrect city!");
    		
    	}else if(!addressField.getText().matches("^[a-zA-Z0-9\\s]*$")){
    		
    		WarningAlert("Incorrect address!");
    	}else{
    		ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
    		Connection con=connectionToDatabase.getConnection();
    		PreparedStatement save = con.prepareStatement("INSERT INTO users(FirstName,LastName,"
    				+ "Password,City,Address,PostalCode,Telephone,Email) VALUES"
    				+ "('"+firstNameField.getText()+"','"+lastNameField.getText()+"','"+
    				passwordField.getText()+"','"+cityField.getText()+"','"+addressField.getText()+"','"
    						+postalCodeField.getText()+"','"+telephoneField.getText()+"','"+
    				emailField.getText()+"')");
    		save.executeUpdate();
    		
    		PreparedStatement getUsers=con.prepareStatement("SELECT LibraryCardNumber FROM Users ");
    		ResultSet rs=getUsers.executeQuery();
    		int ID;
    		
    		rs.last();
    		ID=rs.getInt("LibraryCardNumber");
    		
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("INFORMATION");
			alert.setHeaderText("Your library card number:"+ID);
			alert.showAndWait();
    		
    		con.close();
    		
    	}
    }
    
    void WarningAlert(String message){
    	Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("WARNING");
		alert.setHeaderText(message);
		alert.showAndWait();
    }
    
    @FXML
    void submitMouseEntered(MouseEvent event) {
    	submitButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void submitMouseExited(MouseEvent event) {
    	submitButton.setStyle("-fx-background-color:  #2196f3");
    }
    
    @FXML
    void menuMouseEntered(MouseEvent event) {
    	menuButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void menuMouseExited(MouseEvent event) {
    	menuButton.setStyle("-fx-background-color:  #2196f3");
    }
}
