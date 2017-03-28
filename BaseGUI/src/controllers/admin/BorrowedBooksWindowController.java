package controllers.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import base.Borrows;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mysql.BorrowsTable;
import mysql.BooksTable;

public class BorrowedBooksWindowController {

	private static ArrayList<Borrows> borrowsList;
	BorrowsTable borrowsTable = new BorrowsTable();
	BooksTable booksTable = new BooksTable();

	@FXML
	private TextField textFieldLibraryCardNumber;

	@FXML
	private TableView<Borrows> borrowsTableView;

	@FXML
	private TableColumn<Borrows, String> tableColumnTitle, tableColumnAuthor, tableColumnISBN, tableColumnName,
			tableColumnSurname, tableColumnBorrowDate, tableColumnExpirationDate;
	@FXML
	private TableColumn<Borrows, Integer> tableColumnLibraryCardNumber;	

    @FXML
    private JFXButton returnedButton, deleteButton, menuButton, searchButton;

	@SuppressWarnings("unchecked")
	public void setBorrowsTableView(ObservableList<Borrows> olist) {
		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
		tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
		tableColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		tableColumnLibraryCardNumber.setCellValueFactory(new PropertyValueFactory<>("LibraryCardNumber"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		tableColumnSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
		tableColumnBorrowDate.setCellValueFactory(new PropertyValueFactory<>("BorrowDate"));
		tableColumnExpirationDate.setCellValueFactory(new PropertyValueFactory<>("ExpirationDate"));
		borrowsTableView.setItems(olist);
		borrowsTableView.getColumns().clear();
		borrowsTableView.getColumns().addAll(tableColumnTitle, tableColumnAuthor, tableColumnISBN,
				tableColumnLibraryCardNumber, tableColumnName, tableColumnSurname, tableColumnBorrowDate,
				tableColumnExpirationDate);
	}

	@FXML
	void initialize() throws ClassNotFoundException, SQLException {
		borrowsList = borrowsTable.getBorrowsList();
		ObservableList<Borrows> olist = FXCollections.observableArrayList(borrowsList);
		setBorrowsTableView(olist);
	}

	@FXML
	void deleteAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		int BorrowID = borrowsTableView.getSelectionModel().getSelectedItem().getBorrowID();	
		borrowsTable.deleteFromBorrows(BorrowID);
		
		int BookID = borrowsTableView.getSelectionModel().getSelectedItem().getBookID();
		booksTable.updateBookStatus(BookID,"Yes");
		
		borrowsList = borrowsTable.getBorrowsList();
		ObservableList<Borrows> olist = FXCollections.observableArrayList(borrowsList);
		setBorrowsTableView(olist);
	}
	
    @FXML
    void deleteMouseEntered(MouseEvent event) {
    	deleteButton.setStyle("-fx-background-color:  #e64a19");
    }

    @FXML
    void deleteMouseExited(MouseEvent event) {
    	deleteButton.setStyle("-fx-background-color:  #ef6c00");
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
    	menuButton.setStyle("-fx-background-color:  #e64a19");
    }

    @FXML
    void menuMouseExited(MouseEvent event) {
    	menuButton.setStyle("-fx-background-color:  #ef6c00");
    }

	@FXML
	void returnedAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {		
		int BorrowID = borrowsTableView.getSelectionModel().getSelectedItem().getBorrowID();
		borrowsTable.returnBorrow(BorrowID);
		
		int BookID = borrowsTableView.getSelectionModel().getSelectedItem().getBookID();
		booksTable.updateBookStatus(BookID,"Yes");
		
		borrowsList = borrowsTable.getBorrowsList();
		ObservableList<Borrows> olist = FXCollections.observableArrayList(borrowsList);
		setBorrowsTableView(olist);

	}
	
    @FXML
    void returnedMouseEntered(MouseEvent event) {
    	returnedButton.setStyle("-fx-background-color:  #e64a19");
    }

    @FXML
    void returnedMouseExited(MouseEvent event) {
    	returnedButton.setStyle("-fx-background-color:  #ef6c00");
    }

	@FXML
	void searchAction(ActionEvent event) throws ClassNotFoundException, SQLException {

		borrowsList = borrowsTable.getBorrowsList();
		ArrayList<Borrows> searchList = new ArrayList<>();

		for (Borrows borrow : borrowsList) {

			if (textFieldLibraryCardNumber.getText().isEmpty()) {
				searchList.addAll(borrowsList);
				break;
			} else {
				if (textFieldLibraryCardNumber.getText().equals(borrow.getLibraryCardNumber().toString())) {
					searchList.add(borrow);
				}
			}
		}
		ObservableList<Borrows> olist = FXCollections.observableArrayList(searchList);
		setBorrowsTableView(olist);

	}
	
    @FXML
    void searchMouseEntered(MouseEvent event) {
    	searchButton.setStyle("-fx-background-color:  #e64a19");
    }

    @FXML
    void searchMouseExited(MouseEvent event) {
    	searchButton.setStyle("-fx-background-color:  #ef6c00");
    }

}
