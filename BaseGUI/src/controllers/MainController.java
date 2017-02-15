package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private StackPane stackpane;
    
    @FXML
    void initialize() throws IOException {
    	loadMenu();
    	
    }
    

	public void loadMenu() throws IOException {
		FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("/fxml/StartWindow.fxml"));
    	Pane pane =loader.load();
    	setScreen(pane);
    	
    	StartWindowController startWindowController=loader.getController();
    	startWindowController.setMainControler(this);
	}

	public void setScreen(Pane pane) {
		stackpane.getChildren().clear();
		stackpane.getChildren().add(pane);
	}
	
}
