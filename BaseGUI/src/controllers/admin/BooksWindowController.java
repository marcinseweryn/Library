package controllers.admin;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import base.Book;
import mysql.BorrowsTable;
import mysql.ReservationsTable;
import mysql.Bans;
import mysql.BooksTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BooksWindowController {
	private static ArrayList<Book> booksArrayList;
	
	BooksTable booksTable=new BooksTable();
	ReservationsTable reservationsTable = new ReservationsTable();

	
	@SuppressWarnings("unchecked")
	public void setBaseTableview(ObservableList<Book> olist) {
		tableColumnBookID.setCellValueFactory(new PropertyValueFactory<>("BookID"));
		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		tableColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		tableColumnAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));
		baseTable.setItems(olist);
		baseTable.getColumns().clear();
		baseTable.getColumns().addAll(tableColumnBookID,tableColumnTitle,tableColumnAuthor,
				tableColumnISBN,tableColumnAvailable);
	}
	
	void getBooksTableView() throws ClassNotFoundException, SQLException, IOException{
		booksArrayList=booksTable.getBooks();
		ObservableList<Book> olist=FXCollections.observableArrayList(booksArrayList);
		setBaseTableview(olist);
	}
	
	@FXML
	private JFXTextField textFieldTitle, textFieldAuthor, textFieldISBN;
	
    @FXML
    private JFXButton menuButton, saveButton, editButton, deleteButton, borrowButton, searchButton;
	
	@FXML
	private Text saveInfo,editInfo,deleteInfo;

    @FXML
    private TableView<Book> baseTable;
    
    @FXML
    private TableColumn<Book,String> tableColumnTitle, tableColumnAuthor, tableColumnISBN,
    tableColumnAvailable;
    
    @FXML
    private TableColumn<Book,Integer> tableColumnBookID;
	
	@FXML
    void initialize() throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
		
		reservationsTable.updateExpiredReservations();
		getBooksTableView();
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
    	menuButton.setStyle("-fx-background-color:  #43a047");
    }

    @FXML
    void menuMouseExited(MouseEvent event) {
    	menuButton.setStyle("-fx-background-color:  #7cb342");
    }

	@FXML
	void saveAction(ActionEvent event) throws ClassNotFoundException, IOException, InterruptedException, SQLException {
	
		Book book = new Book(textFieldTitle.getText(), textFieldAuthor.getText(), textFieldISBN.getText(),"Yes");
		booksArrayList.add(book);
					
		booksTable.saveToBooks(book);
		getBooksTableView();
		
		textFieldTitle.clear();textFieldAuthor.clear();textFieldISBN.clear();
		saveInfo.setVisible(true);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
	}
	
	@FXML
	void saveMouseEntered(MouseEvent event) {
		saveButton.setStyle("-fx-background-color:  #43a047");
	}

	@FXML
	void saveMouseExited(MouseEvent event) {
		saveButton.setStyle("-fx-background-color:  #7cb342");
	}


	
	@FXML
	void deleteAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException{
		saveInfo.setVisible(false);
		deleteInfo.setVisible(true);
		editInfo.setVisible(false);

		int BookID=baseTable.getSelectionModel().getSelectedItem().getBookID();				
		booksTable.deleteFromBooks(BookID);

		getBooksTableView();
		textFieldTitle.clear();textFieldAuthor.clear();textFieldISBN.clear();
	}
	
    @FXML
    void deleteMouseEntered(MouseEvent event) {
    	deleteButton.setStyle("-fx-background-color:  #43a047");
    }

    @FXML
    void deleteMouseExited(MouseEvent event) {
    	deleteButton.setStyle("-fx-background-color:  #7cb342");
    }
	
	@FXML
	void editAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		deleteInfo.setVisible(false);
		editInfo.setVisible(true);
		
		int ID=baseTable.getSelectionModel().getSelectedItem().getBookID();
		booksTable.updateBooksRecord(ID,textFieldTitle.getText(),textFieldAuthor.getText(),textFieldISBN.getText());
	
		  
		getBooksTableView();
		textFieldTitle.clear();textFieldAuthor.clear();textFieldISBN.clear();
	}
	
	@FXML
	void editMouseEntered(MouseEvent event) {
		editButton.setStyle("-fx-background-color:  #43a047");
	}

	@FXML
	void editMouseExited(MouseEvent event) {
		editButton.setStyle("-fx-background-color:  #7cb342");
	}	 

	 
	  @FXML
	  void borrowAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {		  		  
		  BorrowsTable borrowsTable = new BorrowsTable();
		  ReservationsTable reservationsTable = new ReservationsTable();
		  Bans ban = new Bans();
		  TextInputDialog dialog = new TextInputDialog();
		  
		  String available = baseTable.getSelectionModel().getSelectedItem().getAvailable();
		  if(available.equals("Yes")){
				dialog.setTitle("BORROW");
				dialog.setHeaderText("You want borrow this book?\n Enter user library card number");
				dialog.setContentText("Library card number:");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					String b=result.get();
					int LibraryCardNumber=Integer.parseInt(b);
					
					if((borrowsTable.getUserBorrowsNumber(LibraryCardNumber)+
							reservationsTable.getUserReservationsNumber(LibraryCardNumber))<=5)
					{
						if(ban.checkBannedUsers(LibraryCardNumber)==false){
						
							int BookID=baseTable.getSelectionModel().getSelectedItem().getBookID();
							booksTable.updateBookStatus(BookID,"No");
							borrowsTable.saveToBorrows(BookID, LibraryCardNumber);
							
							getBooksTableView();
						}else{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("WARNING");
							alert.setHeaderText("User is banned! \nMore information in users window");
							alert.showAndWait();
	
						}
					}else{
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("WARNING");
						alert.setHeaderText("User reached limit of borrowed books and the booking!");
						alert.showAndWait();
					}
				}
		  }else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("You cannot borrow a borrowed book!");
				alert.showAndWait();
		  }
	  }
	  
	  @FXML
	  void borrowMouseEntered(MouseEvent event) {
		  borrowButton.setStyle("-fx-background-color:  #43a047");
	  }

	  @FXML
	  void borrowMouseExited(MouseEvent event) {
		  borrowButton.setStyle("-fx-background-color:  #7cb342");
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
		  searchButton.setStyle("-fx-background-color:  #43a047");
	  }

	  @FXML
	  void searchMouseExited(MouseEvent event) {
		  searchButton.setStyle("-fx-background-color:  #7cb342");
	  }
	  

	 	 	
public	ArrayList<Book> getBase(){
		return booksArrayList;
	}

}
