package controllers.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import base.Reservations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mysql.Bans;
import mysql.BooksTable;
import mysql.BorrowsTable;
import mysql.ReservationsTable;

public class ReservationsWindowController {
	
	private ArrayList<Reservations> reservationsArrayList = new ArrayList<>();
	ReservationsTable reservationsTable = new ReservationsTable();	
	
    @FXML
    private TableView<Reservations> tableViewReservations;
	
    @FXML
    private TableColumn<Reservations, Integer> tableColumnLibraryCardNumber;
    @FXML
    private TableColumn<Reservations, String> tableColumnExpirationDate;
    @FXML
    private TableColumn<Reservations, String> tableColumnTitle, tableColumnAuthor, tableColumnISBN,
    tableColumnName, tableColumnSurname;
    @FXML
    private TextField textFieldLibraryCardNumber;
    
    @SuppressWarnings("unchecked")
	void setReservationsTableView(ObservableList<Reservations> olist){
    	tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
    	tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
    	tableColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    	tableColumnLibraryCardNumber.setCellValueFactory(new PropertyValueFactory<>("LibraryCardNumber"));
    	tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    	tableColumnSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
    	tableColumnExpirationDate.setCellValueFactory(new PropertyValueFactory<>("ExpirationDate"));
    	tableViewReservations.setItems(olist);
    	tableViewReservations.getColumns().clear();
    	tableViewReservations.getColumns().addAll(tableColumnTitle, tableColumnAuthor, tableColumnISBN,
    			tableColumnLibraryCardNumber, tableColumnName, tableColumnSurname, tableColumnExpirationDate);
    }
    
    void getReservationsTable() throws ClassNotFoundException, SQLException{
    	reservationsArrayList = reservationsTable.getReservationsTable(); 
		ObservableList<Reservations> olist = FXCollections.observableArrayList(reservationsArrayList);
		setReservationsTableView(olist);
    }
		
	@FXML
	void initialize() throws ClassNotFoundException, SQLException{	
		reservationsTable.updateExpiredReservations();
		getReservationsTable();
	}
    
    @FXML
    void confirmAction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	BorrowsTable borrowsTable = new BorrowsTable();
    	Bans ban = new Bans();
    	
    	Integer BookID = tableViewReservations.getSelectionModel().getSelectedItem().getBookID();
    	Integer LibraryCardNumber = tableViewReservations.getSelectionModel().getSelectedItem().getLibraryCardNumber();
    	Integer ReservationID = tableViewReservations.getSelectionModel().getSelectedItem().getReservationID();
    	
    	if(ban.checkBannedUsers(LibraryCardNumber)==false){
	    	borrowsTable.saveToBorrows(BookID, LibraryCardNumber);
	    	reservationsTable.confirmReservation(ReservationID);
	    	getReservationsTable();
    	}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("User is banned! \nMore information in users window");
			alert.showAndWait();
    	}
    }

    @FXML
    void deleteAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	BooksTable booksTable = new BooksTable();
    	Integer ReservationID = tableViewReservations.getSelectionModel().getSelectedItem().getReservationID();
    	Integer BookID = tableViewReservations.getSelectionModel().getSelectedItem().getBookID();
    	
    	reservationsTable.deleteFromReservations(ReservationID);
    	booksTable.updateBookStatus(BookID,"Yes");
    	getReservationsTable();
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
    void searchAction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	reservationsArrayList = reservationsTable.getReservationsTable();
    	ArrayList<Reservations> searchResult = new ArrayList<>();
    	
    	for(Reservations reservation:reservationsArrayList){
    		if(reservation.getLibraryCardNumber()==Integer.parseInt(textFieldLibraryCardNumber.getText())){
    			searchResult.add(reservation);
    		}else{}
    	}
    	
		ObservableList<Reservations> olist = FXCollections.observableArrayList(searchResult);
		setReservationsTableView(olist);
    }

}
