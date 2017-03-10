package controllers.admin;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import base.StandardReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mysql.DatesTable;
import mysql.Reports;

public class ReportWindowController {
	
	private static ArrayList<StandardReport> standardReportList;
	
	DatesTable datesTable = new DatesTable();
	Reports reports = new Reports();

    @FXML
    private TableView<StandardReport> tableViewStandardReport;
    
    @FXML
    private JFXDatePicker dateFrom, dateTo;

    @FXML
    private JFXButton submitButton, standardReportButton, mostPopularBooksButton, 
    printToExcelButton, menuButton;

    @FXML
    private TableColumn<StandardReport,Integer> tableColumnBorrowedBooks, tableColumnBookedBooks,
    tableColumnReturnedBooks, tableColumnNewUsers;
    @FXML
    private TableColumn<StandardReport,String> tableColumnDate;
    
    @SuppressWarnings("unchecked")
	public void setStandardReportTableView(ObservableList<StandardReport> olist){
    	tableColumnBorrowedBooks.setCellValueFactory(new PropertyValueFactory<>("BorrowedBooks"));
    	tableColumnBookedBooks.setCellValueFactory(new PropertyValueFactory<>("BookedBooks"));
    	tableColumnReturnedBooks.setCellValueFactory(new PropertyValueFactory<>("ReturnedBooks"));
    	tableColumnNewUsers.setCellValueFactory(new PropertyValueFactory<>("NewUsers"));
    	tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
    	tableViewStandardReport.setItems(olist);
    	tableViewStandardReport.getColumns().clear();
    	tableViewStandardReport.getColumns().addAll(tableColumnDate, tableColumnBorrowedBooks, 
    			tableColumnBookedBooks,tableColumnReturnedBooks, tableColumnNewUsers);  	
    }
    
    @FXML
    void initialize() throws ClassNotFoundException, SQLException{
    	datesTable.checkAndAddDates();

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
    void submitAction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	Date dateF,dateT;
    	dateF = Date.valueOf(dateFrom.getValue());
    	dateT = Date.valueOf(dateTo.getValue());
    	
    	standardReportList = reports.standardReport(dateF, dateT);
    	ObservableList<StandardReport> olist = FXCollections.observableArrayList(standardReportList);
    	setStandardReportTableView(olist);
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
