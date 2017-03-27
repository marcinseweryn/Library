package mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ConnectionToDatabase {
	
private Connection con = null;
	
	public Connection getConnection() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		try {
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library"
					+ "?autoReconnect=true&useSSL=false","root","1234");
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Lost connection!");
			alert.showAndWait();
		}
		System.out.println("connect");
		
		return con;
	}

}
