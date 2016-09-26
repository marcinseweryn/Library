package controllers;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import base.Car;
import base.Save_Read;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class DatabaseWindowController {
	private static ArrayList<Car> base;
	private MainController mainControler;
	private ArrayList<Integer> indexlist= new ArrayList<Integer>();
	OptionsWindowController optionsWindowController=new OptionsWindowController();

	Save_Read sr = new Save_Read();

	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}

	@FXML
	private TextField text1, text2,text3;
	@FXML
	private Text saveInfo,editInfo,deleteInfo;
	@FXML
	private ListView<Car> list;
	@FXML
	private CheckBox checkBoxModel,checkBoxPower,checkBoxPrice;
	  

	  
	
	@FXML
    void initialize() throws ClassNotFoundException, IOException {
		saveInfo.setVisible(false);
		editInfo.setVisible(false);
		deleteInfo.setVisible(false);
		try{
			base=sr.getBase(sr.getBaseName());	
			ObservableList<Car> olist=FXCollections.observableArrayList(base);
			list.setItems(olist);
		}catch(FileNotFoundException e){
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
	void saveAction(ActionEvent event) throws ClassNotFoundException, IOException, InterruptedException {
		base=sr.getBase(sr.getBaseName());
		Car car = new Car(text1.getText(), text2.getText(), text3.getText());
		System.out.println(car.toString());

		base.add(car);
		ObservableList<Car> olist=FXCollections.observableArrayList(base);
		list.setItems(olist);
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
			base.remove(list.getSelectionModel().getSelectedIndex());
			}else{
				int d=indexlist.get(list.getSelectionModel().getSelectedIndex());
				base.remove(d);
				System.out.println(d);	
				System.out.println(indexlist.toString());
			}
		System.out.println("kk");
		ObservableList<Car> olist=FXCollections.observableArrayList(base);
		list.setItems(olist);
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
			base.get(list.getSelectionModel().getSelectedIndex()).setMark(text1.getText());
			base.get(list.getSelectionModel().getSelectedIndex()).setPower(text2.getText());
			base.get(list.getSelectionModel().getSelectedIndex()).setPrice(text3.getText());
		  }else{
			  base.get(indexlist.get(list.getSelectionModel().getSelectedIndex())).setMark(text1.getText());
			  base.get(indexlist.get(list.getSelectionModel().getSelectedIndex())).setPower(text2.getText());
			  base.get(indexlist.get(list.getSelectionModel().getSelectedIndex())).setPrice(text3.getText());
		  }
		ObservableList<Car> olist=FXCollections.observableArrayList(base);
		list.setItems(olist);
		sr.saveList(sr.getBaseName());
		text1.clear();text2.clear();text3.clear();

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
		list.setItems(olist);
		indexlist.addAll(basee.keySet());
		
	  }
	 
	 
	 
	
public	ArrayList<Car> getBase(){
		return base;
	}

}
