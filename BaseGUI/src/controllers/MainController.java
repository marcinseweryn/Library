package controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane stackpane;
    
    @FXML
    void initialize() throws IOException {
    	loadMenu();
    	
    }

	public void loadMenu() throws IOException {
		FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("/fxml/MenuWindow.fxml"));
    	Pane pane =loader.load();
    	setScreen(pane);
    	
    	MenuController menuController=loader.getController();
    	menuController.setMainControler(this);
	}

	public void setScreen(Pane pane) {
		stackpane.getChildren().clear();
		stackpane.getChildren().add(pane);
	}
	
}
