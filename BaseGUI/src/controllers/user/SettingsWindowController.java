package controllers.user;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsWindowController {
	
    @FXML
    private TextField firstNameField, lastNameField, cityField, addressField, postalCodeField,
    telephoneField, emailField;
    
    @FXML
    private PasswordField passwordField;

    @FXML
    void changeAction(ActionEvent event) {

    }

    @FXML
    void menuAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/UserMenuWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }

}
