package controllers.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import base.Ban;
import base.Borrows;
import base.Reservations;
import controllers.LoginWindowController;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mysql.Bans;
import mysql.BooksTable;
import mysql.BorrowsTable;
import mysql.ReservationsTable;

public class UserMenuWindowController {
		
	BorrowsTable borrowsTable = new BorrowsTable();
	ReservationsTable reservationsTable = new ReservationsTable();
	Bans bans = new Bans();
	
    ArrayList<Borrows> borrowsArrayList = new ArrayList<>();
	ArrayList<Reservations> reservationsArrayList = new ArrayList<>();
	
	String banned = LoginWindowController.getBannedInfo();
	static boolean firstShowInfo;
	
    @FXML
    private TableView<Reservations> tableViewReservations;

    @FXML
    private TableView<Borrows> tableViewBorrows;

    @FXML
    private Text textWelcome;
    
    @FXML
    private TableColumn<Reservations, String> tableReservationsColumnTitle, tableReservationsColumnAuthor,
    tableReservationsColumnISBN, tableReservationsColumnBorrowDate, tableReservationsColumnExpirationDate;

    @FXML
    private TableColumn<Borrows, String> tableBorrowsColumnTitle, tableBorrowsColumnAuthor,
    tableBorrowsColumnISBN, tableBorrowsColumnBorrowDate, tableBorrowsColumnExpirationDate;
    
    
  
	@SuppressWarnings("unchecked")
	void setTableViewBorrows(ObservableList<Borrows> olist){
    	tableBorrowsColumnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
    	tableBorrowsColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
    	tableBorrowsColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    	tableBorrowsColumnBorrowDate.setCellValueFactory(new PropertyValueFactory<>("BorrowDate"));
    	tableBorrowsColumnExpirationDate.setCellValueFactory(new PropertyValueFactory<>("ExpirationDate"));
    	tableViewBorrows.setItems(olist);
    	tableViewBorrows.getColumns().clear();
    	tableViewBorrows.getColumns().addAll(tableBorrowsColumnTitle, tableBorrowsColumnAuthor,
    		    tableBorrowsColumnISBN, tableBorrowsColumnBorrowDate, tableBorrowsColumnExpirationDate);
    }
	
    void getTableBorrows() throws ClassNotFoundException, SQLException{
    	borrowsArrayList = borrowsTable.getUserBorrowsList(LoginWindowController.getLibraryCardNumber());
    	ObservableList<Borrows> olist = FXCollections.observableArrayList(borrowsArrayList);
    	setTableViewBorrows(olist);
    }
	
    
    
    @SuppressWarnings("unchecked")
	void setTableViewReservations(ObservableList<Reservations> olist){
    	tableReservationsColumnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
    	tableReservationsColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
    	tableReservationsColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    	tableReservationsColumnBorrowDate.setCellValueFactory(new PropertyValueFactory<>("ReservationDate"));
    	tableReservationsColumnExpirationDate.setCellValueFactory(new PropertyValueFactory<>("ExpirationDate"));
    	tableViewReservations.setItems(olist);
    	tableViewReservations.getColumns().clear();
    	tableViewReservations.getColumns().addAll(tableReservationsColumnTitle, tableReservationsColumnAuthor,
    		    tableReservationsColumnISBN, tableReservationsColumnBorrowDate, tableReservationsColumnExpirationDate);
    	
    }
    
    void getTableReservations() throws ClassNotFoundException, SQLException{
    	reservationsArrayList = reservationsTable.getUserReservationsTable(LoginWindowController.getLibraryCardNumber());
    	ObservableList<Reservations> olist = FXCollections.observableArrayList(reservationsArrayList);
    	setTableViewReservations(olist);
    }
    
    
 
	@FXML
    void initialize() throws ClassNotFoundException, SQLException{
    	textWelcome.setText("Welcome "+LoginWindowController.getName());
    	
    	
    	if(banned.equals("Yes") && firstShowInfo==false){

    		Ban ban = bans.banInformation(LoginWindowController.getLibraryCardNumber());
    		
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("YOU ARE BANNED!!!");
    		alert.setHeaderText("Ban date: "+ban.getBanDate()
    				+"\nExpiration date: "+ban.getExpirationDate()
    				+"\nReason: "+ban.getReason());
    		alert.showAndWait();
    		firstShowInfo = true;
    	}
    	
    	getTableBorrows();
    	getTableReservations();
    	
    }  

    @FXML
    void booksAction(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
    	
    	if(banned.equals("Yes")){
    		Ban ban = bans.banInformation(LoginWindowController.getLibraryCardNumber());
    		
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("YOU ARE BANNED!!!");
    		alert.setHeaderText("Ban date: "+ban.getBanDate()
    				+"\nExpiration date: "+ban.getExpirationDate()
    				+"\nReason: "+ban.getReason());
    		alert.showAndWait();
    	}else{
        	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/BooksWindow.fxml"));
        	Scene scene = new Scene(parent);
        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	
        	stage.setScene(scene);
        	stage.show();
    	}
    }

    @FXML
    void cancelReservationAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	BooksTable booksTable = new BooksTable();
    	Integer ReservationID = tableViewReservations.getSelectionModel().getSelectedItem().getReservationID();
    	Integer BookID = tableViewReservations.getSelectionModel().getSelectedItem().getBookID();
    	
    	reservationsTable.deleteFromReservations(ReservationID);
    	booksTable.updateBookStatus(BookID,"Yes");
    	getTableReservations();
    }

    @FXML
    void historyAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/HistoryWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

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
    void settingsAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/SettingsWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

    }
	
	
}
