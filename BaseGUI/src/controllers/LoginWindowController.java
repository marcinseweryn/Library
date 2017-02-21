package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mysql.BooksTable;
import mysql.ConnectionToDatabase;

public class LoginWindowController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    void backToStartAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/StartWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void loginInAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
    	if(passwordField.getText().equals("1234") && loginField.getText().equals("1111")){
        	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/admin/MenuWindow.fxml"));
        	Scene scene = new Scene(parent);
        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	
        	stage.setScene(scene);
        	stage.show();
    		
    	}else{
    		ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
    		Connection con=connectionToDatabase.getConnection();
    		PreparedStatement getUsers=con.prepareStatement("SELECT LibraryCardNumber,Password FROM Users ");
    		ResultSet rs=getUsers.executeQuery();
    		
    		String password;
    		int ID;
    		boolean passwordOK=false;
    		while(rs.next()){
    			ID=rs.getInt("LibraryCardNumber");
    			password=rs.getString("Password");
    			
    			if(ID==Integer.parseInt(loginField.getText()) && password.equals(passwordField.getText())){   				
    	        	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/UserMenuWindow.fxml"));
    	        	Scene scene = new Scene(parent);
    	        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	        	
    	        	stage.setScene(scene);
    	        	stage.show();
    	        	passwordOK=true;
    	        	break;
    			}
    		}
    		if(passwordOK==false){
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("ERROR");
    			alert.setHeaderText("Wrong password!");
    			alert.showAndWait();
    		}
    		con.close();
    	}
    }
	
}
