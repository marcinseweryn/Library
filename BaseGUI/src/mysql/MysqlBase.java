package mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import base.Book;
import controllers.MainController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MysqlBase {

	private Connection con = null;
	private MainController mainControler;

	public Connection getConnection() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		try {
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/base"
					+ "?autoReconnect=true&useSSL=false","root","1234");
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("UWAGA");
			alert.setHeaderText("Brak polaczenia z baza danych!");
			alert.showAndWait();
			try {
				mainControler.loadMenu();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("connect");
		
		return con;
	}

	public void closeConnection() throws SQLException{
		con.close();
		System.out.println("close");
	}
	
	public ArrayList<Book> getMysqlBase() throws SQLException, ClassNotFoundException, IOException{
		Connection con=getConnection();
		PreparedStatement getbase=con.prepareStatement("SELECT ID,Title,Author,ISBN FROM "+getMySqlTableName()
				+ " ORDER BY ID ASC");
		ResultSet rs=getbase.executeQuery();
		
		ArrayList<Book> list=new ArrayList<Book>();
		String title,author,ISBN;
		int ID;
		while(rs.next()){
			ID=rs.getInt("ID");
			title=rs.getString("Title");
			author=rs.getString("Author");
			ISBN=rs.getString("ISBN");
			Book car=new Book(ID,title,author,ISBN);
			list.add(car);
		}
		closeConnection();
		return list;		
	}
	
	public void deleteFromMysqlBase(int ID) throws ClassNotFoundException, SQLException, IOException
	{
		System.out.println(ID);
		Connection con=getConnection();
		PreparedStatement delete = con.prepareStatement("DELETE FROM "+getMySqlTableName()
			+ " WHERE ID="+ID+"");
		delete.executeUpdate();
	}
	
	public void saveToMysqlBase(Book book) throws ClassNotFoundException, SQLException, IOException{
		Connection con=getConnection();
		PreparedStatement save = con.prepareStatement("INSERT INTO "+getMySqlTableName()
			+ " VALUES('"+book.getID()+"','"+book.getTitle()+"','"+book.getAuthor()
			+"','"+book.getISBN()+"')");
		save.executeUpdate();	
		closeConnection();	
	}
	
	public void updateMysqlBaseRecord(int ID,String title,String author,String ISBN) 
			throws ClassNotFoundException, SQLException, IOException{
		Connection con=getConnection();
		PreparedStatement update = con.prepareStatement("UPDATE "+getMySqlTableName()
				+ " SET Title='"+title+"',Author='"+author+"',ISBN='"+ISBN
				+ "' WHERE ID="+ID);
		update.executeUpdate();
		closeConnection();
	}
	
	
	public void createTable(String tableName) throws ClassNotFoundException, SQLException{
	
			Connection con=getConnection();
		try{
			PreparedStatement create=con.prepareStatement("CREATE TABLE IF NOT EXISTS "+tableName
					+ "(ID int,Title varchar(20),Author varchar(20),ISBN varchar(20),PRIMARY KEY(ID))"
					+ "");
			create.executeUpdate();
		}catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Nieprawid³owa nazwa!");
			alert.showAndWait();
		}
		closeConnection();
	}
	
	public ArrayList<String> getTables() throws SQLException, ClassNotFoundException{
		ArrayList<String> tables=new ArrayList<String>();
		Connection con=getConnection();
		PreparedStatement show=con.prepareStatement("SHOW tables");
		ResultSet rs=show.executeQuery();
		
		while(rs.next()){
			tables.add(rs.getString(1));
		}
		closeConnection();
		return tables;	
	}
	
	public void deleteTable(String tableName) throws ClassNotFoundException{
		Connection con = getConnection();
		try {
			PreparedStatement delete = con.prepareStatement("DROP TABLE "+tableName);
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public String getMySqlTableName() throws IOException{
		String name = null;
		try {
			FileInputStream fis = new FileInputStream("lastMySqlTable");
			DataInputStream dis = new DataInputStream(fis);
			name=dis.readUTF();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		return name;
	}
	
	
	
		
}
