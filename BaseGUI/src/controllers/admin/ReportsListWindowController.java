package controllers.admin;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import base.Book;
import base.Excel;
import base.Save_Read;
import base.Save_Read_BaseList;
import controllers.MainController;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mysql.BooksTable;

public class ReportsListWindowController {

	private MainController mainControler;
	private static ArrayList<Book> base;
	Save_Read sr = new Save_Read();
	private static ArrayList<String> baseList;
	Save_Read_BaseList srbl= new Save_Read_BaseList();
	File file;
	static int baseNumber=0;
	
    public int getBaseNumber() {
		return baseNumber;
	}
    
    @FXML
    private Text selectBaseInfo;   
	@FXML
    private ListView<String> list; 

	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}

	public static ArrayList<String> getBaseList() {
		return baseList;
	}
	
    @FXML
    void initialize() throws ClassNotFoundException, IOException {
    	selectBaseInfo.setVisible(false);;
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
	void deleteBaseAction(ActionEvent event) throws ClassNotFoundException, IOException {
		try{
			baseList=srbl.getBase();
		}catch(FileNotFoundException e){
			emptyDatabaseInfo();
		}
		if(baseList.isEmpty()){
			emptyDatabaseInfo();
		}else{
			/////////////////////////////////////protection before deleting database
			Random random=new Random();
			int a=random.nextInt(899)+100;
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("UWAGA");
			dialog.setHeaderText("Czy napewno chcesz usun¹æ bazê danych?\nWpisz kod aby kontynu³owaæ: "+a);
			dialog.setContentText("Kod:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				String b=result.get();
				int code=Integer.parseInt(b);
				/////////////////////////////////////////////////////////////////////////
				if(a==code)
				{
					file=new File(baseList.get(list.getSelectionModel().getSelectedIndex()));
					baseList.remove(list.getSelectionModel().getSelectedIndex());
					file.delete();
					refreshLocalList();
					srbl.saveList();
				}else{}
			}
		}
	}



	@FXML
	void newBaseAction(ActionEvent event) throws IOException, ClassNotFoundException {

		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Nowa baza");
		dialog.setHeaderText("Nowa baza danych");
		dialog.setContentText("Nazwa:");
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			String baseName = result.get();
			file = new File(baseName);
			base = new ArrayList<Book>();
			FileOutputStream fos = new FileOutputStream(baseName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(base);
			oos.flush();
			oos.close();
			createLastBaseInfoFile(baseName);
			try {
				baseList=srbl.getBase();
			} catch (FileNotFoundException e) {
				file = new File("baseList");
				fos = new FileOutputStream("baseList");
				oos = new ObjectOutputStream(fos);
				baseList = new ArrayList<String>();
				oos.writeObject(baseList);
				oos.flush();
				oos.close();				
			}
			baseList.add(baseName);
			srbl.saveList();
			refreshLocalList();
		}
	}

	@FXML
	void selectBaseAction(ActionEvent event) throws ClassNotFoundException, IOException {
		try{
			baseList=srbl.getBase();
		}catch(FileNotFoundException e){
			emptyDatabaseInfo();
		}
		if(baseList.isEmpty()){
			emptyDatabaseInfo();
		}else{
			baseNumber=list.getSelectionModel().getSelectedIndex();
			createLastBaseInfoFile(list.getSelectionModel().getSelectedItem());
			selectBaseInfo.setText("Wybrano bazê danych: "+sr.getBaseName());
			selectBaseInfo.setVisible(true);
		}
	}

	public void createLastBaseInfoFile(String baseName) throws FileNotFoundException, IOException {
		file = new File("lastBaseInfo"); 
		FileOutputStream fos=new FileOutputStream(file);
		DataOutputStream dos = new DataOutputStream(fos);	//method to open last used database
		dos.writeUTF(baseName);
		dos.close();
	}
	
    @FXML
    void showDatabases(ActionEvent event) throws ClassNotFoundException, IOException {
    	try{
    		baseList=srbl.getBase();
    	}catch(FileNotFoundException e){
    		emptyDatabaseInfo();
    	}
    	if(baseList.isEmpty()){
    		emptyDatabaseInfo();
    	}else{
		    refreshLocalList();}
    }
    
    @FXML
    void copyToMySqlAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	String tableName;
		Book car;
    	tableName=list.getSelectionModel().getSelectedItem();
    	booksTable.createTable(tableName);
    	base=sr.getBase(tableName);
    	selectMySqlTable(tableName);
    	for(int i=0;i<base.size();i++){ 
    		car=base.get(i);
    		car.setBookID(i+1);
    		System.out.println(car.getBookID());
    		booksTable.saveToBooks(car);	
    	}
    	refreshMysqlList();
    }
    
    @FXML
    void joinToMySqlTableAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	int lastID;
    	String tableName=list.getSelectionModel().getSelectedItem();
    	String mySqlTableName=MySqlTableList.getSelectionModel().getSelectedItem();
    	selectMySqlTable(mySqlTableName);
    	base=booksTable.getBooks();
    	lastID=base.get(base.size()-1).getBookID();
    	base=sr.getBase(tableName);
    	for(Book car:base){
    		car.setBookID(++lastID);
    		booksTable.saveToBooks(car);
    	}
    	refreshLocalList();
    }
    
    @FXML
    void createLocalTableExcelFileAction(ActionEvent event) throws IOException, ClassNotFoundException {
    	Excel excel=new Excel();
    	base=sr.getBase(list.getSelectionModel().getSelectedItem());
    	excel.createExelFile(base,list.getSelectionModel().getSelectedItem());
    }

	public void emptyDatabaseInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacja");
		alert.setHeaderText(null);
		alert.setContentText("Brak baz danych!");
		alert.showAndWait();
	}
	
	public void refreshLocalList() {
		ObservableList<String> olist=FXCollections.observableArrayList(baseList);
		list.setItems(olist);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////MySql tables//////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
	
	private static ArrayList<String> MySqlTablesList;
	BooksTable booksTable=new BooksTable();
	
	public void refreshMysqlList() throws SQLException, ClassNotFoundException {
		MySqlTablesList=booksTable.getTables();
    	ObservableList<String> olist=FXCollections.observableArrayList(MySqlTablesList);
    	MySqlTableList.setItems(olist);
	}
	
    @FXML
    private ListView<String> MySqlTableList;
	
	
    @FXML
    void showMySqlTableListAction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	refreshMysqlList();
    }
	
    @FXML
    void newMySqlTableAction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	
    	TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Nowa tabela");
		dialog.setHeaderText("Nowa tabela");
		dialog.setContentText("Nazwa:");
		Optional<String> result = dialog.showAndWait();
    	
		if (result.isPresent()) {
			String name=result.get();
			booksTable.createTable(name);
			refreshMysqlList();
		}	
    }
    
    @FXML
    void deleteMySqlTableAction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	booksTable.deleteTable(MySqlTableList.getSelectionModel().getSelectedItem());
    	System.out.println(MySqlTableList.getSelectionModel().getSelectedItem());
    	refreshMysqlList();
    }
/////////////////////////////////////////////////////////////////////////////////////	
    @FXML
    void selectMySqlTableAction(ActionEvent event) throws IOException {
    	selectMySqlTable(MySqlTableList.getSelectionModel().getSelectedItem()); 	
    }

	public void selectMySqlTable(String tableName) throws FileNotFoundException, IOException {
		File file =new File("lastMySqlTable");
    	FileOutputStream fis=new FileOutputStream(file);
    	DataOutputStream dos=new DataOutputStream(fis);
  
    	dos.writeUTF(tableName);
	}
/////////////////////////////////////////////////////////////////////////////////////	
    @FXML
    void copyMySqlBaseToLocalAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	base=booksTable.getBooks();
    	File file =new File(MySqlTableList.getSelectionModel().getSelectedItem());
    	FileOutputStream fos=new FileOutputStream(file);
    	ObjectOutputStream oos=new ObjectOutputStream(fos);
    	oos.writeObject(base);	
    	oos.close();
    	baseList=srbl.getBase();
    	baseList.add(MySqlTableList.getSelectionModel().getSelectedItem());
    	srbl.saveList();
    	refreshLocalList();
    }
    
    @FXML
    void joinToLocalTablesAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	int lastID;
    	String tableName=list.getSelectionModel().getSelectedItem();
    	String mySqlTableName=MySqlTableList.getSelectionModel().getSelectedItem();
    	selectMySqlTable(mySqlTableName);
    	base=sr.getBase(tableName);   			
    	base.addAll(booksTable.getBooks());  
    	FileOutputStream fos=new FileOutputStream(tableName);
    	ObjectOutputStream oos=new ObjectOutputStream(fos);
    	oos.writeObject(base);
    	oos.close();
    	refreshMysqlList();
    }	
    
    @FXML
    void createMySqlTableExcelFileAction(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    	Excel excel=new Excel();
    	String tableName=MySqlTableList.getSelectionModel().getSelectedItem();
    	selectMySqlTable(tableName);
    	base=booksTable.getBooks();
    	excel.createExelFile(base,tableName);
    }
}
