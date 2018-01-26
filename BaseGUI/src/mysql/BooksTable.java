package mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import base.Book;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BooksTable {



	private ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
	
	public ArrayList<Book> getBooks() throws SQLException, ClassNotFoundException, IOException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement getbase=con.prepareStatement("SELECT * FROM Books"+
				 " ORDER BY BookID ASC");
		ResultSet rs=getbase.executeQuery();
		
		ArrayList<Book> list=new ArrayList<Book>();
		String title,author,ISBN,available;
		int ID;
		while(rs.next()){
			ID=rs.getInt("BookID");
			title=rs.getString("Title");
			author=rs.getString("Author");
			ISBN=rs.getString("ISBN");
			available=rs.getString("Available");
			Book book=new Book(ID,title,author,ISBN,available);
			list.add(book);
		}
		con.close();
		return list;		
	}
	
	public void deleteFromBooks(int BookID) throws ClassNotFoundException, SQLException, IOException
	{
		System.out.println(BookID);
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement delete = con.prepareStatement("DELETE FROM Books "
			+ " WHERE BookID="+BookID+"");
		delete.executeUpdate();
	}
	
	public void saveToBooks(Book book) throws ClassNotFoundException, SQLException, IOException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement save = con.prepareStatement("INSERT INTO Books(Title,Author,ISBN)"
			+ " VALUES('"+book.getTitle()+"','"+book.getAuthor()
			+"','"+book.getISBN()+"')");
		save.executeUpdate();	
		con.close();	
	}
	
	public void updateBooksRecord(int ID,String title,String author,String ISBN) 
			throws ClassNotFoundException, SQLException, IOException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement update = con.prepareStatement("UPDATE  Books"
				+ " SET Title='"+title+"',Author='"+author+"',ISBN='"+ISBN
				+ "' WHERE BookID="+ID);
		update.executeUpdate();
		con.close();
	}
	
	public void updateBookStatus(int ID,String status) 
			throws ClassNotFoundException, SQLException, IOException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement update = con.prepareStatement("UPDATE  Books"
				+ " SET Available='"+status
				+ "' WHERE BookID="+ID);
		update.executeUpdate();
		con.close();
	}
	
	
	public void createTable(String tableName) throws ClassNotFoundException, SQLException{
	
			Connection con=connectionToDatabase.getConnection();
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
		con.close();
	}
	
	public void deleteTable(String tableName) throws ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		try {
			PreparedStatement delete = con.prepareStatement("DROP TABLE "+tableName);
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}	
	
	
		
}
