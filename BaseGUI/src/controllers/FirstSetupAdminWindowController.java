package controllers;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FirstSetupAdminWindowController {
	

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    void saveButton(ActionEvent event) throws IOException {
		FileOutputStream fos = new FileOutputStream("ConfigurationFile",true);
		DataOutputStream dos = new DataOutputStream(fos);
		
		dos.writeUTF(usernameField.getText());
		dos.writeUTF(passwordField.getText());
		
		dos.close();
		fos.close();
		
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/StartWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void initialize() {

    }

}
