package controllers.admin;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import base.Excel;
import base.MostPopularBooks;
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
	private static ArrayList<MostPopularBooks> mostPopularBooksList;
	
	private static Integer excelReport = 1;
	
	private DatesTable datesTable = new DatesTable();
	private Reports reports = new Reports();

    @FXML
    private TableView<StandardReport> tableViewStandardReport;

    @FXML
    private TableView<MostPopularBooks> tableViewMostPopularBooks;
    
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
    
    @FXML
    private TableColumn<MostPopularBooks,String> tableColumnTitle, tableColumnAuthor;
    @FXML
    private TableColumn<MostPopularBooks,Integer> tableColumnBorrows;
    

    
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
    
    @SuppressWarnings("unchecked")
	public void setMostPopularBooksTableView(ObservableList<MostPopularBooks> obList){
    	tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
    	tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
    	tableColumnBorrows.setCellValueFactory(new PropertyValueFactory<>("Borrows"));
    	tableViewMostPopularBooks.setItems(obList);
    	tableViewMostPopularBooks.getColumns().clear();
    	tableViewMostPopularBooks.getColumns().addAll(tableColumnTitle, tableColumnAuthor, 
    			tableColumnBorrows);
    
    }
    
    @FXML
    void initialize() throws ClassNotFoundException, SQLException{
    	datesTable.checkAndAddDates();
    	
    	tableViewMostPopularBooks.setVisible(false);
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
    	tableViewMostPopularBooks.setVisible(true);
    	tableViewStandardReport.setVisible(false);
    	excelReport = 2;
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
    void printToExcelAction(ActionEvent event) throws IOException {
    	Excel excel =new Excel();
    	
    	switch(excelReport){
	    	case 1: excel.createExcelStandardReport(standardReportList); break;
	    	case 2: excel.createExcelMostPopularBooks(mostPopularBooksList); break;
    	}
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
    	tableViewMostPopularBooks.setVisible(false);
    	tableViewStandardReport.setVisible(true);
    	excelReport = 1;

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
    	
    	mostPopularBooksList = reports.mostPopularBooksReport(dateF, dateT);
    	ObservableList<MostPopularBooks> obList = FXCollections.observableArrayList(mostPopularBooksList);
    	setMostPopularBooksTableView(obList);
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
