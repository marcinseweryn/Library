package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import base.Borrows;

public class BorrowsTable {

	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
	
	public void saveToBorrows(Integer BookID,Integer LibraryCardNumber ) throws SQLException, ClassNotFoundException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement save = con.prepareStatement("INSERT INTO borrows(BookID,LibraryCardNumber,ExpirationDate)"
			+ " VALUES("+BookID+","+LibraryCardNumber+",date_add(now(),interval 30 day))");
		save.executeUpdate();	
		con.close();
	}
	
	public ArrayList<Borrows> getBorrowsList() throws ClassNotFoundException, SQLException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement get=con.prepareStatement("SELECT books.BookID,books.Title,books.Author,books.ISBN,"
				+ "users.LibraryCardNumber,users.FirstName,users.LastName,borrows.BorrowDate,"
				+ "borrows.ExpirationDate,borrows.BorrowID "+
					"FROM borrows "+
					"Join users on users.LibraryCardNumber=borrows.LibraryCardNumber "+
					"Join books on books.BookID=borrows.BookID "+
					"WHERE borrows.ReturnDate is null "+
					"ORDER BY BorrowDate DESC");
		ResultSet rs=get.executeQuery();
		
		
		ArrayList<Borrows> borrowsList=new ArrayList<>();
		 String Title,Author,ISBN,Name,Surname;
		 Integer LibraryCardNumber,BorrowID,BookID;
		 Timestamp BorrowDate,ExpirationDate;
		 
		 while(rs.next()){
			Title=rs.getString("Title");
			Author=rs.getString("Author"); 
			ISBN=rs.getString("ISBN");
			Name=rs.getString("FirstName");
			Surname=rs.getString("LastName");
			LibraryCardNumber=rs.getInt("LibraryCardNumber");
			BorrowDate=rs.getTimestamp("BorrowDate");
			ExpirationDate=rs.getTimestamp("ExpirationDate");
			BorrowID=rs.getInt("BorrowID");
			BookID=rs.getInt("BookID");
			@SuppressWarnings("deprecation")
			Borrows borrows = new Borrows(Title, Author, ISBN, Name, Surname, LibraryCardNumber, 
					BorrowID,BookID,BorrowDate.toGMTString(), ExpirationDate.toGMTString());
			borrowsList.add(borrows);
		 }
		con.close();
		return borrowsList;
	}
	
	public void deleteFromBorrows(Integer BorrowID) throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement delete = con.prepareStatement("DELETE FROM borrows WHERE BorrowID="+BorrowID);
		delete.executeUpdate();
		con.close();
	}
	
	public void returnBorrow(Integer BorrowID) throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement ret = con.prepareStatement("UPDATE borrows SET ReturnDate=current_timestamp()"
				+ " WHERE BorrowID="+BorrowID);
		ret.executeUpdate();
		con.close();
	}
	
	public ArrayList<Borrows> getUserBorrowsList(Integer LibraryCardNumber) throws SQLException, ClassNotFoundException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement get=con.prepareStatement("SELECT books.Title,books.Author,books.ISBN,"
				+ "borrows.BorrowDate,borrows.ExpirationDate "+
					"FROM borrows "+
					"Join users on users.LibraryCardNumber=borrows.LibraryCardNumber "+
					"Join books on books.BookID=borrows.BookID "+
					"WHERE borrows.ReturnDate is null and users.LibraryCardNumber="+LibraryCardNumber+
					" ORDER BY BorrowDate DESC");
		ResultSet rs=get.executeQuery();
		
		
		ArrayList<Borrows> borrowsList=new ArrayList<>();
		 String Title,Author,ISBN;
		 Timestamp BorrowDate,ExpirationDate;
		 
		 while(rs.next()){
			Title=rs.getString("Title");
			Author=rs.getString("Author"); 
			ISBN=rs.getString("ISBN");
			BorrowDate=rs.getTimestamp("BorrowDate");
			ExpirationDate=rs.getTimestamp("ExpirationDate");
			@SuppressWarnings("deprecation")
			Borrows borrows = new Borrows(Title, Author, ISBN,BorrowDate.toGMTString(), ExpirationDate.toGMTString());
			borrowsList.add(borrows);
		 }
		con.close();
		return borrowsList;
	}
	
	public ArrayList<Borrows> getUserHistory(Integer LibraryCardNumber) throws SQLException, ClassNotFoundException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement get=con.prepareStatement("SELECT books.Title,books.Author,books.ISBN,"
				+ "borrows.BorrowDate,borrows.ExpirationDate,borrows.ReturnDate "+
					"FROM borrows "+
					"Join users on users.LibraryCardNumber=borrows.LibraryCardNumber "+
					"Join books on books.BookID=borrows.BookID "+
					"WHERE borrows.ReturnDate is not null and users.LibraryCardNumber="+LibraryCardNumber+
					" ORDER BY BorrowDate DESC");
		ResultSet rs=get.executeQuery();
		
		
		ArrayList<Borrows> borrowsList=new ArrayList<>();
		 String Title,Author,ISBN;
		 Timestamp BorrowDate,ExpirationDate,ReturnDate;
		 
		 while(rs.next()){
			Title=rs.getString("Title");
			Author=rs.getString("Author"); 
			ISBN=rs.getString("ISBN");
			BorrowDate=rs.getTimestamp("BorrowDate");
			ExpirationDate=rs.getTimestamp("ExpirationDate");
			ReturnDate=rs.getTimestamp("ReturnDate");
			@SuppressWarnings("deprecation")
			Borrows borrows = new Borrows(Title, Author, ISBN,BorrowDate.toGMTString(), ExpirationDate.toGMTString(),
					ReturnDate.toGMTString());
			borrowsList.add(borrows);
		 }
		con.close();
		return borrowsList;
		
	}
	
}
