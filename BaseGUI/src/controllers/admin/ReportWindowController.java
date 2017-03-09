package controllers.admin;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReportWindowController {

    @FXML
    private JFXDatePicker dateFrom, dateTo;

    @FXML
    private JFXButton submitButton, standardReportButton, mostPopularBooksButton, 
    printToExcelButton, menuButton;

    
    
    @FXML
    void initialize(){

    }
    
    @FXML
    void menuAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/admin/MenuWindow.fxml"));
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

    @FXML
    void mostPopularBooksAction(ActionEvent event) {

    }

    @FXML
    void mostPopularBooksMouseEntered(MouseEvent event) {
    	mostPopularBooksButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void mostPopularBooksMouseExited(MouseEvent event) {
    	mostPopularBooksButton.setStyle("-fx-background-color:  #2196f3");
    }

    @FXML
    void printToExcelAction(ActionEvent event) {

    }

    @FXML
    void printToExcelMouseEntered(MouseEvent event) {
    	printToExcelButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void printToExcelMouseExited(MouseEvent event) {
    	printToExcelButton.setStyle("-fx-background-color:  #2196f3");
    }

    @FXML
    void standardReportAction(ActionEvent event) {
    

    }

    @FXML
    void standardReportMouseEntered(MouseEvent event) {
    	standardReportButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void standardReportMouseExited(MouseEvent event) {
    	standardReportButton.setStyle("-fx-background-color:  #2196f3");
    }

    @FXML
    void submitAction(ActionEvent event) {

    }

    @FXML
    void submitMouseEntered(MouseEvent event) {
    	submitButton.setStyle("-fx-background-color:  #673ab7");

    }

    @FXML
    void submitMouseExited(MouseEvent event) {
    	submitButton.setStyle("-fx-background-color:  #2196f3");
    }
}
