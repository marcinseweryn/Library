package controllers.admin;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import base.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mysql.UsersTable;

public class UsersWindowController {
	
	private ArrayList<Users> usersArrayList = new ArrayList<>();
	UsersTable usersTable = new UsersTable();
	
    @FXML
    private TableView<Users> usersTableView;
    @FXML
    private TextField textFieldPasswordChange, textFieldID, textFieldName, textFieldSurname;
    @FXML
    private TableColumn<Users, Integer> tableColumnLibraryCardNumber, tableColumnBorrows, 
    tableColumnExpirationDates; 
    @FXML
    private TableColumn<Users, String> tableColumnName, tableColumnSurname, tableColumnCity,
    tableColumnAddress, tableColumnPostalCode, tableColumnTelephone, tableColumnEmail, tableColumnBanned;

     

	public void setUsersTableView(ObservableList<Users> olist){
    	tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    	tableColumnSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
    	tableColumnCity.setCellValueFactory(new PropertyValueFactory<>("City"));
    	tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
    	tableColumnPostalCode.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));;
    	tableColumnTelephone.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
    	tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    	tableColumnBanned.setCellValueFactory(new PropertyValueFactory<>("Banned"));
    	tableColumnLibraryCardNumber.setCellValueFactory(new PropertyValueFactory<>("LibraryCardNumber"));
    	tableColumnExpirationDates.setCellValueFactory(new PropertyValueFactory<>("ExpirationDates"));
    	tableColumnBorrows.setCellValueFactory(new PropertyValueFactory<>("Borrows"));
		usersTableView.setItems(olist);
		usersTableView.getColumns().clear();
		usersTableView.getColumns().addAll(tableColumnLibraryCardNumber,tableColumnName, tableColumnSurname, tableColumnCity,
		tableColumnAddress, tableColumnPostalCode, tableColumnTelephone, tableColumnEmail,tableColumnBorrows,
		tableColumnExpirationDates,tableColumnBanned);
    }
    
	void getUsersTableView() throws ClassNotFoundException, SQLException, IOException{
		usersArrayList=usersTable.getUserTable();
		ObservableList<Users> olist=FXCollections.observableArrayList(usersArrayList);
		setUsersTableView(olist);
	}
    
	@FXML
    void initialize() throws ClassNotFoundException, SQLException, IOException {
    	getUsersTableView();
    }
    
    
    @FXML
    void banAction(ActionEvent event) {

    }

    @FXML
    void deleteAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	TextInputDialog dialog = new TextInputDialog();    
		Random random=new Random();
		int code=random.nextInt(899)+100;
		Integer LibraryCardNumber = usersTableView.getSelectionModel().getSelectedItem().getLibraryCardNumber();
		dialog.setTitle("WARNING");
		dialog.setHeaderText("Are you sure? You really want delete this user?"
				+ "\nEnter code to delete user: "+code);
		dialog.setContentText("Code:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			Integer codeResult= Integer.parseInt(result.get());			
			if(code==codeResult){
					usersTable.deleteUser(LibraryCardNumber);
					getUsersTableView();
			}else{}
		}
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
    void searchAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	usersArrayList = usersTable.getUserTable();
    	ArrayList<Users> searchResults =new ArrayList<>();
    	
    	for(Users user:usersArrayList){
    		///////////////////////////LEVEL 0/////////////////////////////////////////////   	
	    	if(textFieldID.getText().isEmpty() && textFieldName.getText().isEmpty() && textFieldSurname.getText().isEmpty()){
	    		searchResults=usersArrayList;
	    		break;
	    	}else{
	    		////////////////////////LEVEL 1//////////////////////////////////////////////
	    		if(textFieldID.getText().isEmpty() && textFieldName.getText().isEmpty()){
	    			if(user.getSurname().equals(textFieldSurname.getText())){
	    				searchResults.add(user);
	    			}else{}	    			
	    		}else{
	    			if(textFieldID.getText().isEmpty() && textFieldSurname.getText().isEmpty()){
	    				if(user.getName().equals(textFieldName.getText())){
	    					searchResults.add(user);
	    				}else{}
	    			}else{
	    				if(textFieldName.getText().isEmpty() && textFieldSurname.getText().isEmpty()){
	    					if(user.getLibraryCardNumber()==Integer.parseInt(textFieldID.getText())){
	    						searchResults.add(user);
	    					}else{}
	    				}else{
	    					////////////////////////LEVEL 2/////////////////////////////////////////
	    					if(textFieldID.getText().isEmpty()){
	    						if(user.getName().equals(textFieldName.getText()) && user.getSurname().equals(textFieldSurname.getText())){
	    							searchResults.add(user);
	    						}else{}
	    					}else{
	    						if(textFieldName.getText().isEmpty()){
	    							if((user.getLibraryCardNumber()==Integer.parseInt(textFieldID.getText()))&& user.getSurname().equals(textFieldSurname.getText())){
	    								searchResults.add(user);
	    							}else{}
	    						}else{
	    							if(textFieldSurname.getText().isEmpty()){
	    								if((user.getLibraryCardNumber()==Integer.parseInt(textFieldID.getText())) && user.getName().equals(textFieldName.getText())){
	    									searchResults.add(user);
	    								}else{}
	    							}else{
	    								///////////////////////////LEVEL 3////////////////////////////////////
	    								if((user.getLibraryCardNumber()==Integer.parseInt(textFieldID.getText())) && user.getName().equals(textFieldName.getText()) && user.getSurname().equals(textFieldSurname.getText())){
	    									searchResults.add(user);
	    								}else{}					
	    							}
	    						}
	    					}
	    				}
	    			}	
	    		}  		
	    	}
    	}
		ObservableList<Users> olist=FXCollections.observableArrayList(searchResults);
		setUsersTableView(olist);
    }

    @FXML
    void submitAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	Integer LibraryCardNumber = usersTableView.getSelectionModel().getSelectedItem().getLibraryCardNumber();
		String password=textFieldPasswordChange.getText();	
		
		usersTable.changeUserPassword(LibraryCardNumber, password);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("INFORMATION");
		alert.setHeaderText("Password has been changed");
		alert.showAndWait();
    }

    @FXML
    void unbanAction(ActionEvent event) {

    }
}