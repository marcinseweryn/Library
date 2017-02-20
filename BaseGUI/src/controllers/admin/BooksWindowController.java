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
import mysql.MysqlBase;
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
	private static ArrayList<Book> booksTable;
	private ArrayList<Book> indexlist= new ArrayList<Book>();
	
	Save_Read sr = new Save_Read();
	MysqlBase mysqlBase=new MysqlBase();

	
	public void setBaseTableview(ObservableList<Book> olist) {
		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		tableColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		tableColumnAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));
		baseTable.setItems(olist);
		baseTable.getColumns().clear();
		baseTable.getColumns().addAll(tableColumnTitle,tableColumnAuthor,tableColumnISBN,tableColumnAvailable);
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
    private TableColumn<Book,String> tableColumnTitle;
    @FXML
    private TableColumn<Book,String> tableColumnAuthor;
    @FXML
    private TableColumn<Book,String> tableColumnISBN;
    @FXML
    private TableColumn<Book,String> tableColumnAvailable;
	
	@FXML
    void initialize() throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
		
			booksTable=mysqlBase.getBooks();
			ObservableList<Book> olist=FXCollections.observableArrayList(booksTable);
			setBaseTableview(olist);

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
		booksTable.add(book);
			
		ObservableList<Book> olist=FXCollections.observableArrayList(booksTable);	
		setBaseTableview(olist);
		
		mysqlBase.saveToBooks(book);
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
		booksTable=mysqlBase.getBooks();

		if(indexlist.isEmpty()==true){
			int getIndex=baseTable.getSelectionModel().getSelectedIndex();
						
			mysqlBase.deleteFromBooks((booksTable.get(getIndex).getBookID()));
			}else{
				int d=indexlist.get(baseTable.getSelectionModel().getSelectedIndex()).getBookID();
				mysqlBase.deleteFromBooks(d);
				System.out.println(d);	
				System.out.println(indexlist.toString());
			}
		System.out.println("kk");
		booksTable=mysqlBase.getBooks();
		ObservableList<Book> olist=FXCollections.observableArrayList(booksTable);
		setBaseTableview(olist);
		text1.clear();text2.clear();text3.clear();
	}
	
	 @FXML
	 void editAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		deleteInfo.setVisible(false);
		editInfo.setVisible(true);
		
		booksTable=mysqlBase.getBooks();
		if(indexlist.isEmpty()==true){
			int ID=baseTable.getSelectionModel().getSelectedItem().getBookID();
			mysqlBase.updateBooksRecord(ID,text1.getText(),text2.getText(),text3.getText());
		  }else{
			int ID=indexlist.get(baseTable.getSelectionModel().getSelectedIndex()).getBookID();
			mysqlBase.updateBooksRecord(ID,text1.getText(),text2.getText(),text3.getText());
		  }
		booksTable=mysqlBase.getBooks();
		ObservableList<Book> olist=FXCollections.observableArrayList(booksTable);
		setBaseTableview(olist);
		text1.clear();text2.clear();text3.clear();
	}
	 
	  @FXML
	  void borrowAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		  BorrowsTable borrowsTable = new BorrowsTable();
		  TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("BORROW");
			dialog.setHeaderText("You want borrow this book?\n Enter user library card number");
			dialog.setContentText("Library card number:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				String b=result.get();
				int LibraryCardNumber=Integer.parseInt(b);
								
				booksTable=mysqlBase.getBooks();
				if(indexlist.isEmpty()==true){				
					int BookID=booksTable.get(baseTable.getSelectionModel().getSelectedIndex()).getBookID();
					mysqlBase.updateBookStatus(BookID,"No");
					borrowsTable.seveToBorrows(BookID, LibraryCardNumber);
				  }else{
					int BookID=indexlist.get(baseTable.getSelectionModel().getSelectedIndex()).getBookID();
					mysqlBase.updateBookStatus(BookID,"No");
					borrowsTable.seveToBorrows(BookID, LibraryCardNumber);
				  }
				
				}
	  }
	 
	  @FXML
	  void searchAction(ActionEvent event) throws ClassNotFoundException, IOException {
		  Map<Integer,Book> basee=new HashMap<Integer,Book>();
		try {
			booksTable=mysqlBase.getBooks();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		indexlist.removeAll(indexlist);
		
		if(checkBoxTitle.isSelected()==false && checkBoxAuthor.isSelected()==false && checkBoxISBN.isSelected()==false)
		{	
			
		}else{
			for(Book book:booksTable)
			{
				if(checkBoxTitle.isSelected()==true && checkBoxAuthor.isSelected()==true && checkBoxISBN.isSelected()==true)
				{
					if(book.getTitle().equals(text1.getText()) && book.getAuthor().equals(text2.getText()) && book.getISBN().equals(text3.getText()) )
					{
						basee.put(booksTable.get(booksTable.indexOf(book)).getBookID(),book);
					}else{}
				}else{
					if(checkBoxTitle.isSelected()==true && checkBoxAuthor.isSelected()==true)
					{
						if(book.getTitle().equals(text1.getText()) && book.getAuthor().equals(text2.getText()))
						{
							basee.put(booksTable.get(booksTable.indexOf(book)).getBookID(),book);
						}else{}
					}else{
						if(checkBoxTitle.isSelected()==true && checkBoxISBN.isSelected()==true)
						{
							if(book.getTitle().equals(text1.getText()) && book.getISBN().equals(text3.getText()))
							{
								basee.put(booksTable.indexOf(book),book);
							}else{}
						}else{
							if(checkBoxAuthor.isSelected()==true && checkBoxISBN.isSelected()==true)
							{
								if(book.getISBN().equals(text3.getText()) && book.getAuthor().equals(text2.getText()))
								{
									basee.put(booksTable.get(booksTable.indexOf(book)).getBookID(),book);
								}else{}
							}else{
								if(checkBoxTitle.isSelected()==true)
								{
									if(book.getTitle().equals(text1.getText()))
									{
										basee.put(booksTable.get(booksTable.indexOf(book)).getBookID(),book);
									}else{}	
								}else{
									if(checkBoxAuthor.isSelected()==true)
									{
										if(book.getAuthor().equals(text2.getText()))
										{
											basee.put(booksTable.get(booksTable.indexOf(book)).getBookID(),book);
										}else{}
									}else{
										if(checkBoxISBN.isSelected()==true)
										{
											if(book.getISBN().equals(text3.getText()))
											{
												basee.put(booksTable.get(booksTable.indexOf(book)).getBookID(),book);
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
		indexlist.addAll(basee.values());
		
	  }
	 	 	
public	ArrayList<Book> getBase(){
		return booksTable;
	}

}
