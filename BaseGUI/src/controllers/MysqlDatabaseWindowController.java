package controllers;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import base.Book;
import base.Save_Read;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MysqlDatabaseWindowController {
	private static ArrayList<Book> base;
	private ArrayList<Book> indexlist= new ArrayList<Book>();
	
	Save_Read sr = new Save_Read();
	MysqlBase mysqlBase=new MysqlBase();

	
	public void setBaseTableview(ObservableList<Book> olist) {
		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		tableColumnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		baseTable.setItems(olist);
		baseTable.getColumns().clear();
		baseTable.getColumns().addAll(tableColumnTitle,tableColumnAuthor,tableColumnISBN);
	}
	
	@FXML
	private TextField text1, text2,text3;
	@FXML
	private Text saveInfo,editInfo,deleteInfo;
	@FXML
	private CheckBox checkBoxTitle,checkBoxAuthor,checkBoxISBN;
	@FXML
    private Text baseInfo;
	
    @FXML
    private TableView<Book> baseTable;
    @FXML
    private TableColumn<Book,String> tableColumnTitle;
    @FXML
    private TableColumn<Book,String> tableColumnAuthor;
    @FXML
    private TableColumn<Book,String> tableColumnISBN;
	
	@FXML
    void initialize() throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
		
			baseInfo.setText("Baza danych: "+mysqlBase.getMySqlTableName());
			base=mysqlBase.getMysqlBase();
			ObservableList<Book> olist=FXCollections.observableArrayList(base);
			setBaseTableview(olist);

	}

	@FXML
	void menuAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/MenuWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

	}

	@FXML
	void saveAction(ActionEvent event) throws ClassNotFoundException, IOException, InterruptedException, SQLException {
		base=mysqlBase.getMysqlBase();	
		int lastID;
		try{
			lastID=base.get(base.size()-1).getID();
			lastID+=1;
		}catch(ArrayIndexOutOfBoundsException e){
			lastID=1;
		}
		System.out.println(lastID);
		Book car = new Book(lastID,text1.getText(), text2.getText(), text3.getText());
		base.add(car);
			
		ObservableList<Book> olist=FXCollections.observableArrayList(base);	
		setBaseTableview(olist);
		
		mysqlBase.saveToMysqlBase(car);
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
		base=mysqlBase.getMysqlBase();

		if(indexlist.isEmpty()==true){
			int getIndex=baseTable.getSelectionModel().getSelectedIndex();
						
			mysqlBase.deleteFromMysqlBase((base.get(getIndex).getID()));
			}else{
				int d=indexlist.get(baseTable.getSelectionModel().getSelectedIndex()).getID();
				mysqlBase.deleteFromMysqlBase(d);
				System.out.println(d);	
				System.out.println(indexlist.toString());
			}
		System.out.println("kk");
		base=mysqlBase.getMysqlBase();
		ObservableList<Book> olist=FXCollections.observableArrayList(base);
		setBaseTableview(olist);
		text1.clear();text2.clear();text3.clear();
	}
	
	 @FXML
	 void editAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		deleteInfo.setVisible(false);
		editInfo.setVisible(true);
		
		base=mysqlBase.getMysqlBase();
		if(indexlist.isEmpty()==true){
			int ID=base.get(baseTable.getSelectionModel().getSelectedIndex()).getID();
			mysqlBase.updateMysqlBaseRecord(ID,text1.getText(),text2.getText(),text3.getText());
		  }else{
			int ID=indexlist.get(baseTable.getSelectionModel().getSelectedIndex()).getID();
			mysqlBase.updateMysqlBaseRecord(ID,text1.getText(),text2.getText(),text3.getText());
		  }
		base=mysqlBase.getMysqlBase();
		ObservableList<Book> olist=FXCollections.observableArrayList(base);
		setBaseTableview(olist);
		text1.clear();text2.clear();text3.clear();
	}
	 
	  @FXML
	  void searchAction(ActionEvent event) throws ClassNotFoundException, IOException {
		  Map<Integer,Book> basee=new HashMap<Integer,Book>();
		try {
			base=mysqlBase.getMysqlBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		indexlist.removeAll(indexlist);
		
		if(checkBoxTitle.isSelected()==false && checkBoxAuthor.isSelected()==false && checkBoxISBN.isSelected()==false)
		{	
			
		}else{
			for(Book book:base)
			{
				if(checkBoxTitle.isSelected()==true && checkBoxAuthor.isSelected()==true && checkBoxISBN.isSelected()==true)
				{
					if(book.getTitle().equals(text1.getText()) && book.getAuthor().equals(text2.getText()) && book.getISBN().equals(text3.getText()) )
					{
						basee.put(base.get(base.indexOf(book)).getID(),book);
					}else{}
				}else{
					if(checkBoxTitle.isSelected()==true && checkBoxAuthor.isSelected()==true)
					{
						if(book.getTitle().equals(text1.getText()) && book.getAuthor().equals(text2.getText()))
						{
							basee.put(base.get(base.indexOf(book)).getID(),book);
						}else{}
					}else{
						if(checkBoxTitle.isSelected()==true && checkBoxISBN.isSelected()==true)
						{
							if(book.getTitle().equals(text1.getText()) && book.getISBN().equals(text3.getText()))
							{
								basee.put(base.indexOf(book),book);
							}else{}
						}else{
							if(checkBoxAuthor.isSelected()==true && checkBoxISBN.isSelected()==true)
							{
								if(book.getISBN().equals(text3.getText()) && book.getAuthor().equals(text2.getText()))
								{
									basee.put(base.get(base.indexOf(book)).getID(),book);
								}else{}
							}else{
								if(checkBoxTitle.isSelected()==true)
								{
									if(book.getTitle().equals(text1.getText()))
									{
										basee.put(base.get(base.indexOf(book)).getID(),book);
									}else{}	
								}else{
									if(checkBoxAuthor.isSelected()==true)
									{
										if(book.getAuthor().equals(text2.getText()))
										{
											basee.put(base.get(base.indexOf(book)).getID(),book);
										}else{}
									}else{
										if(checkBoxISBN.isSelected()==true)
										{
											if(book.getISBN().equals(text3.getText()))
											{
												basee.put(base.get(base.indexOf(book)).getID(),book);
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
		return base;
	}

}
