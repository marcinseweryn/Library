package controllers.admin;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import base.Book;
import base.Save_Read;
import controllers.MainController;
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

public class BooksOfflineWindowController {
	private static ArrayList<Book> base;
	private MainController mainControler;
	private ArrayList<Integer> indexlist= new ArrayList<Integer>();

	Save_Read sr = new Save_Read();
	MysqlBase mysqlBase=new MysqlBase();

	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}
	
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
	private CheckBox checkBoxModel,checkBoxPower,checkBoxPrice;
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
		
			baseInfo.setText("Baza danych: "+sr.getBaseName());
			base=sr.getBase(sr.getBaseName());	
			ObservableList<Book> olist=FXCollections.observableArrayList(base);
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
	void saveAction(ActionEvent event) throws ClassNotFoundException, IOException, InterruptedException {
		base=sr.getBase(sr.getBaseName());
		Book car = new Book(text1.getText(), text2.getText(), text3.getText(),"Yes");
		System.out.println(car.toString());
		base.add(car);
			
		ObservableList<Book> olist=FXCollections.observableArrayList(base);	
		setBaseTableview(olist);
		
		sr.saveList(sr.getBaseName());
		text1.clear();text2.clear();text3.clear();
		saveInfo.setVisible(true);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
	}


	
	@FXML
	void deleteAction(ActionEvent event) throws ClassNotFoundException, IOException{
		saveInfo.setVisible(false);
		deleteInfo.setVisible(true);
		editInfo.setVisible(false);
		base=sr.getBase(sr.getBaseName());
		
		if(indexlist.isEmpty()==true){
			base.remove(baseTable.getSelectionModel().getSelectedIndex());
			}else{
				int d=indexlist.get(baseTable.getSelectionModel().getSelectedIndex());
				base.remove(d);
				System.out.println(d);	
				System.out.println(indexlist.toString());
			}
		System.out.println("kk");
		ObservableList<Book> olist=FXCollections.observableArrayList(base);
		setBaseTableview(olist);
		sr.saveList(sr.getBaseName());
		text1.clear();text2.clear();text3.clear();
		
	}
	
	 @FXML
	 void editAction(ActionEvent event) throws ClassNotFoundException, IOException {
		saveInfo.setVisible(false);
		deleteInfo.setVisible(false);
		editInfo.setVisible(true);

		base=sr.getBase(sr.getBaseName());
		if(indexlist.isEmpty()==true){
			base.get(baseTable.getSelectionModel().getSelectedIndex()).setTitle(text1.getText());
			base.get(baseTable.getSelectionModel().getSelectedIndex()).setAuthor(text2.getText());
			base.get(baseTable.getSelectionModel().getSelectedIndex()).setISBN(text3.getText());
		  }else{
			  base.get(indexlist.get(baseTable.getSelectionModel().getSelectedIndex())).setTitle(text1.getText());
			  base.get(indexlist.get(baseTable.getSelectionModel().getSelectedIndex())).setAuthor(text2.getText());
			  base.get(indexlist.get(baseTable.getSelectionModel().getSelectedIndex())).setISBN(text3.getText());
		  }
		ObservableList<Book> olist=FXCollections.observableArrayList(base);
		setBaseTableview(olist);
		sr.saveList(sr.getBaseName());
		text1.clear();text2.clear();text3.clear();

	}
	 
	  @FXML
	  void searchAction(ActionEvent event) throws ClassNotFoundException, IOException {
		  Map<Integer,Book> basee=new HashMap<Integer,Book>();
		base=sr.getBase(sr.getBaseName());
		indexlist.removeAll(indexlist);
		
		if(checkBoxModel.isSelected()==false && checkBoxPower.isSelected()==false && checkBoxPrice.isSelected()==false)
		{	
			
		}else{
			for(Book car:base)
			{
				if(checkBoxModel.isSelected()==true && checkBoxPower.isSelected()==true && checkBoxPrice.isSelected()==true)
				{
					if(car.getTitle().equals(text1.getText()) && car.getAuthor().equals(text2.getText()) && car.getISBN().equals(text3.getText()) )
					{
						basee.put(base.indexOf(car),car);
					}else{}
				}else{
					if(checkBoxModel.isSelected()==true && checkBoxPower.isSelected()==true)
					{
						if(car.getTitle().equals(text1.getText()) && car.getAuthor().equals(text2.getText()))
						{
							basee.put(base.indexOf(car),car);
						}else{}
					}else{
						if(checkBoxModel.isSelected()==true && checkBoxPrice.isSelected()==true)
						{
							if(car.getTitle().equals(text1.getText()) && car.getISBN().equals(text3.getText()))
							{
								basee.put(base.indexOf(car),car);
							}else{}
						}else{
							if(checkBoxPower.isSelected()==true && checkBoxPrice.isSelected()==true)
							{
								if(car.getISBN().equals(text3.getText()) && car.getAuthor().equals(text2.getText()))
								{
									basee.put(base.indexOf(car),car);
								}else{}
							}else{
								if(checkBoxModel.isSelected()==true)
								{
									if(car.getTitle().equals(text1.getText()))
									{
										basee.put(base.indexOf(car),car);
									}else{}	
								}else{
									if(checkBoxPower.isSelected()==true)
									{
										if(car.getAuthor().equals(text2.getText()))
										{
											basee.put(base.indexOf(car),car);
										}else{}
									}else{
										if(checkBoxPrice.isSelected()==true)
										{
											if(car.getISBN().equals(text3.getText()))
											{
												basee.put(base.indexOf(car),car);
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
		indexlist.addAll(basee.keySet());
		
	  }
	 	 	
public	ArrayList<Book> getBase(){
		return base;
	}

}
