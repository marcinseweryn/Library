package base;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Base extends Application {

	public static void main(String[] args)  throws ClassNotFoundException, IOException {
		
		launch(args);		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/StackPaneWindow.fxml"));
		StackPane stackPane = loader.load();
		
		Scene scene = new Scene(stackPane,600,400);

		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Baza");
		
	}

}
