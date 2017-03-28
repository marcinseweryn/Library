package controllers.admin;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mysql.BooksTable;

public class MenuController {

	BooksTable booksTable = new BooksTable();

    @FXML
    private JFXButton reservationsButton, booksButton, borrowedBooksButton, usersButton,
    reportButton, logOffButton;
	
	@FXML
	void initialize(){
		
	}

	@FXML
    void booksAction(ActionEvent event) throws IOException{

    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/admin/BooksWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
    	stage.setScene(scene);
    	stage.show();
	
    }
	
    @FXML
    void booksMouseEntered(MouseEvent event) {
    	booksButton.setStyle("-fx-background-color:  #43a047");
    }

    @FXML
    void booksMouseExited(MouseEvent event) {
    	booksButton.setStyle("-fx-background-color:  #7cb342");
    }
	
	@FXML
	void listOfReportsAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/admin/ReportsListWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
	}	
	

    @FXML
    void usersAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/admin/UsersWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    void usersMouseEntered(MouseEvent event) {
    	usersButton.setStyle("-fx-background-color:  #00796b");
    }

    @FXML
    void usersMouseExited(MouseEvent event) {
    	usersButton.setStyle("-fx-background-color:   #009688");
    }

    @FXML
    void borrowedBooksAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/admin/BorrowedBooksWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    void borrowedBooksMouseEntered(MouseEvent event) {
    	borrowedBooksButton.setStyle("-fx-background-color:  #e64a19");
    }

    @FXML
    void borrowedBooksMouseExited(MouseEvent event) {
    	borrowedBooksButton.setStyle("-fx-background-color:  #ef6c00");
    }

    @FXML
    void logOffAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/StartWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

    }
    
    @FXML
    void logOffMouseEntered(MouseEvent event) {
    	logOffButton.setStyle("-fx-background-color:  #c62828");
    }

    @FXML
    void logOffMouseExited(MouseEvent event) {
    	logOffButton.setStyle("-fx-background-color:   #e53935");
    }

    @FXML
    void reportAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/admin/ReportWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

    }
    
    @FXML
    void reportMouseEntered(MouseEvent event) {
    	reportButton.setStyle("-fx-background-color:  #1976d2");
    }

    @FXML
    void reportMouseExited(MouseEvent event) {
    	reportButton.setStyle("-fx-background-color:   #2196f3");
    }

    @FXML
    void reservationsAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/admin/ReservationsWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

    }
    
    @FXML
    void reservationsMouseEntered(MouseEvent event) {
    	reservationsButton.setStyle("-fx-background-color:  #c51162");
    }

    @FXML
    void reservationsMouseExited(MouseEvent event) {
    	reservationsButton.setStyle("-fx-background-color:  #e91e63");
    }
	
}
