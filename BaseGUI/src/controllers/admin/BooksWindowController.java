package controllers.admin;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import base.Book;
import base.Save_Read;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BooksWindowController {
	private static ArrayList<Book> booksArrayList;
	
	Save_Read sr = new Save_Read();
	BooksTable booksTable=new BooksTable();
	ReservationsTable reservationsTable = new ReservationsTable();

	
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
	private TextField text1, text2,text3;
	@FXML
	private Text saveInfo,editInfo,deleteInfo;
	@FXML
	private CheckBox checkBoxTitle,checkBoxAuthor,checkBoxISBN;

	
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
		
		reservationsTable.deleteExpiredReservations();
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
	void saveAction(ActionEvent event) throws ClassNotFoundException, IOException, InterruptedException, SQLException {
	
		Book book = new Book(text1.getText(), text2.getText(), text3.getText(),"Yes");
		booksArrayList.add(book);
					
		booksTable.saveToBooks(book);
		getBooksTableView();
		
		text1.clear();text2.clear();text3.clear();
		saveInfo.setVisible(true);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
	}


	
	@FXML
	void deleteAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException{
		saveInfo.setVisible(false);
		deleteInfo.setVisible(true);
		editInfo.setVisible(false);

		int BookID=baseTable.getSelectionModel().getSelectedItem().getBookID();				
		booksTable.deleteFromBooks(BookID);

		getBooksTableView();
		text1.clear();text2.clear();text3.clear();
	}
	
	 @FXML
	 void editAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		deleteInfo.setVisible(false);
		editInfo.setVisible(true);
		
		int ID=baseTable.getSelectionModel().getSelectedItem().getBookID();
		booksTable.updateBooksRecord(ID,text1.getText(),text2.getText(),text3.getText());
	
		  
		getBooksTableView();
		text1.clear();text2.clear();text3.clear();
	}
	 
	  @FXML
	  void borrowAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {		  		  
		  BorrowsTable borrowsTable = new BorrowsTable();
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
				}
		  }else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("You cannot borrow a borrowed book!");
				alert.showAndWait();
		  }
	  }
	 
	  @FXML
	  void searchAction(ActionEvent event) throws ClassNotFoundException, IOException {
		  Map<Integer,Book> basee=new HashMap<Integer,Book>();
		try {
			booksArrayList=booksTable.getBooks();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(checkBoxTitle.isSelected()==false && checkBoxAuthor.isSelected()==false && checkBoxISBN.isSelected()==false)
		{	
			
		}else{
			for(Book book:booksArrayList)
			{
				if(checkBoxTitle.isSelected()==true && checkBoxAuthor.isSelected()==true && checkBoxISBN.isSelected()==true)
				{
					if(book.getTitle().equals(text1.getText()) && book.getAuthor().equals(text2.getText()) && book.getISBN().equals(text3.getText()) )
					{
						basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
					}else{}
				}else{
					if(checkBoxTitle.isSelected()==true && checkBoxAuthor.isSelected()==true)
					{
						if(book.getTitle().equals(text1.getText()) && book.getAuthor().equals(text2.getText()))
						{
							basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
						}else{}
					}else{
						if(checkBoxTitle.isSelected()==true && checkBoxISBN.isSelected()==true)
						{
							if(book.getTitle().equals(text1.getText()) && book.getISBN().equals(text3.getText()))
							{
								basee.put(booksArrayList.indexOf(book),book);
							}else{}
						}else{
							if(checkBoxAuthor.isSelected()==true && checkBoxISBN.isSelected()==true)
							{
								if(book.getISBN().equals(text3.getText()) && book.getAuthor().equals(text2.getText()))
								{
									basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
								}else{}
							}else{
								if(checkBoxTitle.isSelected()==true)
								{
									if(book.getTitle().equals(text1.getText()))
									{
										basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
									}else{}	
								}else{
									if(checkBoxAuthor.isSelected()==true)
									{
										if(book.getAuthor().equals(text2.getText()))
										{
											basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
										}else{}
									}else{
										if(checkBoxISBN.isSelected()==true)
										{
											if(book.getISBN().equals(text3.getText()))
											{
												basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
											}else{}
										}else{
											
										}	
									}	
								}	
							}		
						}
					}
				}											
			}							
		}
		ObservableList<Book> olist=FXCollections.observableArrayList(basee.values());
		setBaseTableview(olist);		
	  }
	 	 	
public	ArrayList<Book> getBase(){
		return booksArrayList;
	}

}
