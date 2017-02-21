package controllers.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mysql.BooksTable;

public class BooksWindowController {
	
	BooksTable booksTable = new BooksTable();
	
	private static ArrayList<Book> booksArrayList;
	private ArrayList<Book> indexlist= new ArrayList<Book>();
	
    @FXML
    private TextField titleTextField, authorTextField, ISBNtextField;

    @FXML
    private CheckBox checkBoxTitle, checkBoxAuthor,checkBoxISBN;

    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private TableColumn<Book, String> tableColumnTitle, tableColumnAuthor, tableColumnISBN,
    tableColumnAvailable;
	
	
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
    void reservationAction(ActionEvent event) {

    }

    @FXML
    void searchAction(ActionEvent event) throws ClassNotFoundException, IOException {
    	  Map<Integer,Book> basee=new HashMap<Integer,Book>();
  		try {
  			booksArrayList=booksTable.getBooks();
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
  		indexlist.removeAll(indexlist);
  		
  		if(checkBoxTitle.isSelected()==false && checkBoxAuthor.isSelected()==false && checkBoxISBN.isSelected()==false)
  		{	
  			
  		}else{
  			for(Book book:booksArrayList)
  			{
  				if(checkBoxTitle.isSelected()==true && checkBoxAuthor.isSelected()==true && checkBoxISBN.isSelected()==true)
  				{
  					if(book.getTitle().equals(titleTextField.getText()) && book.getAuthor().equals(authorTextField.getText()) && book.getISBN().equals(ISBNtextField.getText()) )
  					{
  						basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
  					}else{}
  				}else{
  					if(checkBoxTitle.isSelected()==true && checkBoxAuthor.isSelected()==true)
  					{
  						if(book.getTitle().equals(titleTextField.getText()) && book.getAuthor().equals(authorTextField.getText()))
  						{
  							basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
  						}else{}
  					}else{
  						if(checkBoxTitle.isSelected()==true && checkBoxISBN.isSelected()==true)
  						{
  							if(book.getTitle().equals(titleTextField.getText()) && book.getISBN().equals(ISBNtextField.getText()))
  							{
  								basee.put(booksArrayList.indexOf(book),book);
  							}else{}
  						}else{
  							if(checkBoxAuthor.isSelected()==true && checkBoxISBN.isSelected()==true)
  							{
  								if(book.getISBN().equals(ISBNtextField.getText()) && book.getAuthor().equals(authorTextField.getText()))
  								{
  									basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
  								}else{}
  							}else{
  								if(checkBoxTitle.isSelected()==true)
  								{
  									if(book.getTitle().equals(titleTextField.getText()))
  									{
  										basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
  									}else{}	
  								}else{
  									if(checkBoxAuthor.isSelected()==true)
  									{
  										if(book.getAuthor().equals(authorTextField.getText()))
  										{
  											basee.put(booksArrayList.get(booksArrayList.indexOf(book)).getBookID(),book);
  										}else{}
  									}else{
  										if(checkBoxISBN.isSelected()==true)
  										{
  											if(book.getISBN().equals(ISBNtextField.getText()))
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
  		indexlist.addAll(basee.values());
  		
    }

}
