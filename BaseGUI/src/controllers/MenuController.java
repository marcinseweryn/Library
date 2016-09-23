package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import base.Car;
import base.Save_Read;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MenuController {

	 private MainController mainControler;
	 
	   public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}

	@FXML
	   void addAction(ActionEvent event) throws IOException {
		   FXMLLoader loader = new FXMLLoader();
		   loader.setLocation(this.getClass().getResource("/fxml/DatabaseWindow.fxml"));
		   Pane pane=loader.load();
		   mainControler.setScreen(pane);
		   
		   DatabaseWindowController addController=loader.getController();
		   addController.setMainControler(mainControler);
		   
	    }
	private static ArrayList<Car> base;
	Save_Read sr = new Save_Read();


	 @FXML
	    void newAction(ActionEvent event) throws IOException {
		 File file=new File("base");
			base=new ArrayList<Car>();	
			FileOutputStream fos=new FileOutputStream("base");
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(base);
			oos.flush();
			oos.close();

	    }
}
