package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import base.Car;
import base.Save_Read;
import base.Save_Read_BaseList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MenuController {

	private MainController mainControler;
	Save_Read_BaseList srbl= new Save_Read_BaseList();
	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}

	
	@FXML
	void initialize(){
		
	}

	@FXML
	void addAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/DatabaseWindow.fxml"));
		Pane pane = loader.load();
		mainControler.setScreen(pane);

		DatabaseWindowController addController = loader.getController();
		addController.setMainControler(mainControler);

	}

	@FXML
    void mysqlBaseAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/MysqlDatabaseWindow.fxml"));
		Pane pane = loader.load();
		mainControler.setScreen(pane);

		MysqlDatabaseWindowController addController = loader.getController();
		addController.setMainControler(mainControler);
    }
	
	@FXML
	void optionsAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/OptionsWindow.fxml"));
		Pane pane = loader.load();
		mainControler.setScreen(pane);

		OptionsWindowController addController = loader.getController();
		addController.setMainControler(mainControler);
	}	    
}
