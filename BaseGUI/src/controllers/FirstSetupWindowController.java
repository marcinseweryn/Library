package controllers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
import mysql.FirstStart;

public class FirstSetupWindowController {

	private FirstStart firstStart = new FirstStart();
	
    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXTextField hostnameField, usernameField;

    @FXML
    private JFXPasswordField passwordField;


	@SuppressWarnings("unused")
	private MainController mainControler;
	
	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}
    

    @FXML
    void saveButton(ActionEvent event) {
		try{		
			FileOutputStream fos = new FileOutputStream("ConfigurationFile");
			DataOutputStream dos = new DataOutputStream(fos);
			
			dos.writeUTF(hostnameField.getText());
			dos.writeUTF(usernameField.getText());
			dos.writeUTF(passwordField.getText());
			
			dos.close();
			fos.close();
			firstStart.CreateDatabase();
			
	    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/FirstSetupAdminWindow.fxml"));
	    	Scene scene = new Scene(parent);
	    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	
	    	stage.setScene(scene);
	    	stage.show();
			
		}catch(Exception ex){
			File file = new File("ConfigurationFile");
			file.delete();
			ex.printStackTrace();

		}

    }

    @FXML
    void initialize() {

    }
	
}
