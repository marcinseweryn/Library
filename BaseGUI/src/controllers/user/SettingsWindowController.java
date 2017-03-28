package controllers.user;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import base.Users;
import controllers.LoginWindowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mysql.UsersTable;

public class SettingsWindowController {
	
	UsersTable usersTable = new UsersTable();

	
    @FXML
    private JFXTextField  firstNameField, lastNameField, cityField, addressField, postalCodeField,
    telephoneField, emailField;
    
    @FXML
    private JFXPasswordField  passwordField;
    
    @FXML
    private JFXButton saveButton, menuButton;
    
    @FXML
    void initialize() throws ClassNotFoundException, SQLException{
    	Users user = usersTable.getUserSettings(LoginWindowController.getLibraryCardNumber());

    	firstNameField.setText(user.getName());
    	lastNameField.setText(user.getName());
    	passwordField.setText(user.getPassword());
    	cityField.setText(user.getCity());
    	addressField.setText(user.getAddress());
    	postalCodeField.setText(user.getPostalCode());
    	telephoneField.setText(user.getTelephone());
    	emailField.setText(user.getEmail());
    }
    
    @FXML
    void saveAction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	
    	if(firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
    			cityField.getText().isEmpty() || addressField.getText().isEmpty() ||
    			postalCodeField.getText().isEmpty() || telephoneField.getText().isEmpty() ||
    			emailField.getText().isEmpty() || passwordField.getText().isEmpty()){
    		
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("All fields must be filled!");
			alert.showAndWait();
    		
    	}else{
    		usersTable.setUserSettings(LoginWindowController.getLibraryCardNumber(),firstNameField.getText(),
    			lastNameField.getText(), cityField.getText(), addressField.getText(), postalCodeField.getText(),
    			telephoneField.getText(), emailField.getText(), passwordField.getText());
    	
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("INFORMATION");
			alert.setHeaderText("Your data has been changed");
			alert.showAndWait();
    	}

    }
    
    @FXML
    void saveMouseEntered(MouseEvent event) {
    	saveButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void saveMouseExited(MouseEvent event) {
    	saveButton.setStyle("-fx-background-color:  #2196f3");
    }

    @FXML
    void menuAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/UserMenuWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
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
