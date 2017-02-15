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

import base.Book;
import base.Save_Read;
import base.Save_Read_BaseList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mysql.MysqlBase;

public class MenuController {

	private static ArrayList<Book> base;
	Save_Read sr = new Save_Read();
	MysqlBase mysqlBase = new MysqlBase();

	
	@FXML
	void initialize(){
		
	}

	@FXML
	void addAction(ActionEvent event){
		try{
		///////////////////////////////////
		base=sr.getBase(sr.getBaseName());	//check before action
		///////////////////////////////////
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/DatabaseWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
		}catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Lack table!");
			alert.setContentText("Go do the option to select a table or create a new one");
			alert.showAndWait();  
		}

	}

	@FXML
    void mysqlBaseAction(ActionEvent event){
		try{
		///////////////////////////////	
		base=mysqlBase.getMysqlBase();	//check before action
		////////////////////////////////
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/MysqlDatabaseWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
    	stage.setScene(scene);
    	stage.show();
		}catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Lack table!");
			alert.setContentText("Go do the option to select a table or create a new one");
			alert.showAndWait();  
		}
    }
	
	@FXML
	void optionsAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/OptionsWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
	}	    
}
