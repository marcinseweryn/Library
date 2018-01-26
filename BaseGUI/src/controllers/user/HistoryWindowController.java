package controllers.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import base.Borrows;
import controllers.LoginWindowController;
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
import mysql.BorrowsTable;

public class HistoryWindowController {
	
	private BorrowsTable borrowsTable = new BorrowsTable();
	
	private ArrayList<Borrows> historyArrayList= new ArrayList<>(); 
	
    @FXML
    private TableView<Borrows> tableViewHistory;

    @FXML
    private TableColumn<Borrows, String> tableColumnTitle, tableColumnAuthor, tableColumnISBN,
    tableColumnBorrowDate, tableColumnExpirationDate, tableColumnReturnDate;
	   
    
	@SuppressWarnings("unchecked")
	void setTableViewHistory(ObservableList<Borrows> olist){
    	tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
    	tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
    	tableColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    	tableColumnBorrowDate.setCellValueFactory(new PropertyValueFactory<>("BorrowDate"));
    	tableColumnExpirationDate.setCellValueFactory(new PropertyValueFactory<>("ExpirationDate"));
    	tableColumnReturnDate.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
    	tableViewHistory.setItems(olist);
    	tableViewHistory.getColumns().clear();
    	tableViewHistory.getColumns().addAll(tableColumnTitle, tableColumnAuthor, tableColumnISBN,
    			tableColumnBorrowDate, tableColumnExpirationDate, tableColumnReturnDate);
    }
	
    void getTableHistory() throws ClassNotFoundException, SQLException{
    	historyArrayList = borrowsTable.getUserHistory(LoginWindowController.getLibraryCardNumber());
    	ObservableList<Borrows> olist = FXCollections.observableArrayList(historyArrayList);
    	setTableViewHistory(olist);
    }
	
    
    @FXML
    void initialize() throws ClassNotFoundException, SQLException{
    	getTableHistory();
    }
    
	
    @FXML
    void menuAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/UserMenuWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

    }
    

    @FXML
    void menuMouseEntered(MouseEvent event) {

    }

    @FXML
    void menuMouseExited(MouseEvent event) {

    }

}
