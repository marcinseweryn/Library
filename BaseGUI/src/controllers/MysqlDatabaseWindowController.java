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
	private ArrayList<Integer> indexlist= new ArrayList<Integer>();
	
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
			baseInfo.setText("Baza danych: "+sr.getBaseName());
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
		mysqlBase.closeConnection();
	}

	@FXML
	void menuAction(ActionEvent event) throws IOException {
		mainControler.loadMenu();

	}

	@FXML
	void saveAction(ActionEvent event) throws ClassNotFoundException, IOException, InterruptedException, SQLException {
		base=mysqlBase.getMysqlBase();
		int ID;
		int lastID;
		if(base.isEmpty()){
			ID=1;
		}else{
			lastID=base.get(base.size()-1).getID();
			ID=(1+lastID);
			System.out.println(ID);
		}
		Car car = new Car(ID,text1.getText(), text2.getText(), text3.getText());
		System.out.println(car.toString());
		base.add(car);
			
		ObservableList<Car> olist=FXCollections.observableArrayList(base);	
		setBaseTableview(olist);
		
		mysqlBase.saveToMysqlBase(car,base);
		text1.clear();text2.clear();text3.clear();
		saveInfo.setVisible(true);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
		mysqlBase.closeConnection();
	}


	
	@FXML
	void deleteAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException{
		saveInfo.setVisible(false);
		deleteInfo.setVisible(true);
		editInfo.setVisible(false);
		
		
		if(indexlist.isEmpty()==true){
			mysqlBase.deleteFromMysqlBase(baseTable.getSelectionModel().getSelectedItem().getID());
			}else{
				int d=indexlist.get(baseTable.getSelectionModel().getSelectedIndex());
				base.remove(d);
				System.out.println(d);	
				System.out.println(indexlist.toString());
			}
		System.out.println("kk");
		base=mysqlBase.getMysqlBase();
		ObservableList<Car> olist=FXCollections.observableArrayList(base);
		setBaseTableview(olist);
		text1.clear();text2.clear();text3.clear();
		mysqlBase.closeConnection();
	}
	
	 @FXML
	 void editAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		saveInfo.setVisible(false);
		deleteInfo.setVisible(false);
		editInfo.setVisible(true);


		if(indexlist.isEmpty()==true){
			int ID=baseTable.getSelectionModel().getSelectedIndex();
			mysqlBase.updateMysqlBaseRecord(ID,text1.getText(),text2.getText(),text3.getText());
		  }else{
			  base.get(indexlist.get(baseTable.getSelectionModel().getSelectedIndex())).setMark(text1.getText());
			  base.get(indexlist.get(baseTable.getSelectionModel().getSelectedIndex())).setPower(text2.getText());
			  base.get(indexlist.get(baseTable.getSelectionModel().getSelectedIndex())).setPrice(text3.getText());
		  }
		base=mysqlBase.getMysqlBase();
		ObservableList<Car> olist=FXCollections.observableArrayList(base);
		setBaseTableview(olist);
		text1.clear();text2.clear();text3.clear();
		mysqlBase.closeConnection();
	}
	 
	  @FXML
	  void searchAction(ActionEvent event) throws ClassNotFoundException, IOException {
		  Map<Integer,Car> basee=new HashMap<Integer,Car>();
		base=sr.getBase(sr.getBaseName());
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
						basee.put(base.indexOf(car),car);
					}else{}
				}else{
					if(checkBoxModel.isSelected()==true && checkBoxPower.isSelected()==true)
					{
						if(car.getMark().equals(text1.getText()) && car.getPower().equals(text2.getText()))
						{
							basee.put(base.indexOf(car),car);
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
									basee.put(base.indexOf(car),car);
								}else{}
							}else{
								if(checkBoxModel.isSelected()==true)
								{
									if(car.getMark().equals(text1.getText()))
									{
										basee.put(base.indexOf(car),car);
									}else{}	
								}else{
									if(checkBoxPower.isSelected()==true)
									{
										if(car.getPower().equals(text2.getText()))
										{
											basee.put(base.indexOf(car),car);
										}else{}
									}else{
										if(checkBoxPrice.isSelected()==true)
										{
											if(car.getPrice().equals(text3.getText()))
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
		ObservableList<Car> olist=FXCollections.observableArrayList(basee.values());
		setBaseTableview(olist);
		indexlist.addAll(basee.keySet());
		
	  }
	 	 	
public	ArrayList<Car> getBase(){
		return base;
	}

}
