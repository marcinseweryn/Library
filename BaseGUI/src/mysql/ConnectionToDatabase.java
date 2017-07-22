package mysql;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ConnectionToDatabase {
	
private Connection con = null;
private String hostname, username, password, databaseName = "library";
	

	public void setDatabaseName(String databaseName){
		this.databaseName = databaseName;
	}

	public Connection getConnection() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		
		try {
			FileInputStream fis = new FileInputStream("ConfigurationFile");
			DataInputStream dis =  new DataInputStream(fis);
			
			try {
				hostname = dis.readUTF();
				username = dis.readUTF();
				password = dis.readUTF();
			} catch (IOException e) {

				e.printStackTrace();
			}
			
			try {
				dis.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			con= DriverManager.getConnection("jdbc:mysql://"+hostname+":3306/"+databaseName
					+ "?autoReconnect=true&useSSL=false",""+username+"",""+password+"");
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Cannot conection to database. Check your connection or database settings.");
			alert.showAndWait();
		}
		System.out.println("connect");
		
		return con;
	}

}
