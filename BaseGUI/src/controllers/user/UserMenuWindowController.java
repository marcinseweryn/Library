package controllers.user;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserMenuWindowController {


    @FXML
    void booksAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/BooksWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void cancelReservationAction(ActionEvent event) {

    }

    @FXML
    void historyAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/HistoryWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

    }

    @FXML
    void logOffAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/StartWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();
    	
    }

    @FXML
    void settingsAction(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/user/SettingsWindow.fxml"));
    	Scene scene = new Scene(parent);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	stage.setScene(scene);
    	stage.show();

    }
	
	
}
