package controllers.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import base.Book;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mysql.BooksTable;
import mysql.BorrowsTable;
import mysql.ReservationsTable;

public class BooksWindowController {
	
	BooksTable booksTable = new BooksTable();
	ReservationsTable reservationsTable = new ReservationsTable();
	
	private static ArrayList<Book> booksArrayList;
	
    @FXML
    private JFXTextField textFieldTitle, textFieldAuthor, textFieldISBN;

    @FXML
    private JFXButton menuButton, reservationButton, searchButton;
    
    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private TableColumn<Book, String> tableColumnTitle, tableColumnAuthor, tableColumnISBN,
    tableColumnAvailable;
	
	
	@SuppressWarnings("unchecked")
	public void setBaseTableview(ObservableList<Book> olist) {
		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		tableColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		tableColumnAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));
		booksTableView.setItems(olist);
		booksTableView.getColumns().clear();
		booksTableView.getColumns().addAll(tableColumnTitle,tableColumnAuthor,tableColumnISBN,tableColumnAvailable);
	}
	
	
	@FXML
    void initialize() throws ClassNotFoundException, IOException, SQLException {
			reservationsTable.updateExpiredReservations();
			getBooksTableView();
	}


	private void getBooksTableView() throws SQLException, ClassNotFoundException, IOException {
		booksArrayList=booksTable.getBooks();
		ObservableList<Book> olist=FXCollections.observableArrayList(booksArrayList);
		setBaseTableview(olist);
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
    	menuButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void menuMouseExited(MouseEvent event) {
    	menuButton.setStyle("-fx-background-color:  #2196f3");
    }

    @FXML
    void reservationAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	ReservationsTable reservationsTable = new ReservationsTable();
    	BorrowsTable borrowsTable = new BorrowsTable();
    	
		Integer LibraryCardNumber = LoginWindowController.getLibraryCardNumber();
		String available = booksTableView.getSelectionModel().getSelectedItem().getAvailable();
		if(available.equals("Yes")){
			
			if((borrowsTable.getUserBorrowsNumber(LibraryCardNumber)+
					reservationsTable.getUserReservationsNumber(LibraryCardNumber))<=5){
				
				    Integer BookID = booksTableView.getSelectionModel().getSelectedItem().getBookID();
						
					reservationsTable.saveReservation(LibraryCardNumber, BookID);
					booksTable.updateBookStatus(BookID,"No");
							
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("INFORMATION");
					alert.setHeaderText("You have two days to borrow this book");
					alert.showAndWait();
						
					getBooksTableView();
			}else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("You reached limit of borrowed books and the booking!");
				alert.showAndWait();
			}
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("You cannot borrow a borrowed book!");
			alert.showAndWait();
		}
    }
    
    @FXML
    void reservationMouseEntered(MouseEvent event) {
    	reservationButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void reservationMouseExited(MouseEvent event) {
    	reservationButton.setStyle("-fx-background-color:  #2196f3");
    }

    @FXML
    void searchAction(ActionEvent event) throws ClassNotFoundException, IOException {

  		try {
  			booksArrayList=booksTable.getBooks();
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}

  		ArrayList<Book> searchResults =new ArrayList<>();
    	
    	for(Book book:booksArrayList){
    		///////////////////////////LEVEL 0/////////////////////////////////////////////   	
	    	if(textFieldTitle.getText().isEmpty() && textFieldAuthor.getText().isEmpty() && textFieldISBN.getText().isEmpty()){
	    		searchResults=booksArrayList;
	    		break;
	    	}else{
	    		////////////////////////LEVEL 1//////////////////////////////////////////////
	    		if(textFieldTitle.getText().isEmpty() && textFieldAuthor.getText().isEmpty()){
	    			if(book.getISBN().equals(textFieldISBN.getText())){
	    				searchResults.add(book);
	    			}else{}	    			
	    		}else{
	    			if(textFieldTitle.getText().isEmpty() && textFieldISBN.getText().isEmpty()){
	    				if(book.getAuthor().equals(textFieldAuthor.getText())){
	    					searchResults.add(book);
	    				}else{}
	    			}else{
	    				if(textFieldAuthor.getText().isEmpty() && textFieldISBN.getText().isEmpty()){
	    					if(book.getTitle().equals(textFieldTitle.getText())){
	    						searchResults.add(book);
	    					}else{}
	    				}else{
	    					////////////////////////LEVEL 2/////////////////////////////////////////
	    					if(textFieldTitle.getText().isEmpty()){
	    						if(book.getAuthor().equals(textFieldAuthor.getText()) && book.getISBN().equals(textFieldISBN.getText())){
	    							searchResults.add(book);
	    						}else{}
	    					}else{
	    						if(textFieldAuthor.getText().isEmpty()){
	    							if((book.getTitle().equals(textFieldTitle.getText()))&& book.getISBN().equals(textFieldISBN.getText())){
	    								searchResults.add(book);
	    							}else{}
	    						}else{
	    							if(textFieldISBN.getText().isEmpty()){
	    								if((book.getTitle().equals(textFieldTitle.getText())) && book.getAuthor().equals(textFieldAuthor.getText())){
	    									searchResults.add(book);
	    								}else{}
	    							}else{
	    								///////////////////////////LEVEL 3////////////////////////////////////
	    								if((book.getTitle().equals(textFieldTitle.getText())) && book.getAuthor().equals(textFieldAuthor.getText()) && book.getISBN().equals(textFieldISBN.getText())){
	    									searchResults.add(book);
	    								}else{}					
	    							}
	    						}
	    					}
	    				}
	    			}	
	    		}  		
	    	}
    	}


  		ObservableList<Book> olist=FXCollections.observableArrayList(searchResults);
  		setBaseTableview(olist);

  		
    }
    
    @FXML
    void searchMouseEntered(MouseEvent event) {
    	searchButton.setStyle("-fx-background-color:  #673ab7");
    }

    @FXML
    void searchMouseExited(MouseEvent event) {
    	searchButton.setStyle("-fx-background-color:  #2196f3");
    }

}
