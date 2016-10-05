package controllers;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import base.Car;
import base.Save_Read;
import mysql.MysqlBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class MysqlDatabaseWindowController {
	private static ArrayList<Car> base;
	private MainController mainControler;
	private ArrayList<Car> indexlist= new ArrayList<Car>();
	
	Save_Read sr = new Save_Read();
	MysqlBase mysqlBase=new MysqlBase();

	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}
	
	public void setBaseTableview(ObservableList<Car> olist) {
		tableColumnMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
		tableColumnPower.setCellValueFactory(new PropertyValueFactory<>("power"));
		tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		baseTable.setItems(olist);
		baseTable.getColumns().clear();
		baseTable.getColumns().addAll(tableColumnMark,tableColumnPower,tableColumnPrice);
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
    private TableView<Car> baseTable;
    @FXML
    private TableColumn<Car,String> tableColumnMark;
    @FXML
    private TableColumn<Car,String> tableColumnPower;
    @FXML
    private TableColumn<Car,String> tableColumnPrice;
	
	@FXML
    void initialize() throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
		try{
			baseInfo.setText("Baza danych: "+mysqlBase.getMySqlTableName());
			base=mysqlBase.getMysqlBase();
			ObservableList<Car> olist=FXCollections.observableArrayList(base);
			setBaseTableview(olist);
		}catch(IOException e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("UWAGA");
			alert.setHeaderText("Brak bazy danych!");
			alert.setContentText("PrzejdŸ do opcji aby wybraæ bazê danych lub utworzyæ now¹");
			alert.showAndWait();
			mainControler.loadMenu();
		}
	}

	@FXML
	void menuAction(ActionEvent event) throws IOException {
		mainControler.loadMenu();

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
		Car car = new Car(lastID,text1.getText(), text2.getText(), text3.getText());
		base.add(car);
			
		ObservableList<Car> olist=FXCollections.observableArrayList(base);	
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
		ObservableList<Car> olist=FXCollections.observableArrayList(base);
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
		ObservableList<Car> olist=FXCollections.observableArrayList(base);
		setBaseTableview(olist);
		text1.clear();text2.clear();text3.clear();
	}
	 
	  @FXML
	  void searchAction(ActionEvent event) throws ClassNotFoundException, IOException {
		  Map<Integer,Car> basee=new HashMap<Integer,Car>();
		try {
			base=mysqlBase.getMysqlBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		indexlist.removeAll(indexlist);
		
		if(checkBoxModel.isSelected()==false && checkBoxPower.isSelected()==false && checkBoxPrice.isSelected()==false)
		{	
			
		}else{
			for(Car car:base)
			{
				if(checkBoxModel.isSelected()==true && checkBoxPower.isSelected()==true && checkBoxPrice.isSelected()==true)
				{
					if(car.getMark().equals(text1.getText()) && car.getPower().equals(text2.getText()) && car.getPrice().equals(text3.getText()) )
					{
						basee.put(base.get(base.indexOf(car)).getID(),car);
					}else{}
				}else{
					if(checkBoxModel.isSelected()==true && checkBoxPower.isSelected()==true)
					{
						if(car.getMark().equals(text1.getText()) && car.getPower().equals(text2.getText()))
						{
							basee.put(base.get(base.indexOf(car)).getID(),car);
						}else{}
					}else{
						if(checkBoxModel.isSelected()==true && checkBoxPrice.isSelected()==true)
						{
							if(car.getMark().equals(text1.getText()) && car.getPrice().equals(text3.getText()))
							{
								basee.put(base.indexOf(car),car);
							}else{}
						}else{
							if(checkBoxPower.isSelected()==true && checkBoxPrice.isSelected()==true)
							{
								if(car.getPrice().equals(text3.getText()) && car.getPower().equals(text2.getText()))
								{
									basee.put(base.get(base.indexOf(car)).getID(),car);
								}else{}
							}else{
								if(checkBoxModel.isSelected()==true)
								{
									if(car.getMark().equals(text1.getText()))
									{
										basee.put(base.get(base.indexOf(car)).getID(),car);
									}else{}	
								}else{
									if(checkBoxPower.isSelected()==true)
									{
										if(car.getPower().equals(text2.getText()))
										{
											basee.put(base.get(base.indexOf(car)).getID(),car);
										}else{}
									}else{
										if(checkBoxPrice.isSelected()==true)
										{
											if(car.getPrice().equals(text3.getText()))
											{
												basee.put(base.get(base.indexOf(car)).getID(),car);
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
		ObservableList<Car> olist=FXCollections.observableArrayList(basee.values());
		setBaseTableview(olist);
		indexlist.addAll(basee.values());
		
	  }
	 	 	
public	ArrayList<Car> getBase(){
		return base;
	}

}
