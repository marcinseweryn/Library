package mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import base.Borrows;

public class BorrowsTable {

	MysqlBase mysqlBase = new MysqlBase();
	
	public void seveToBorrows(Integer BookID,Integer LibraryCardNumber ) throws SQLException, ClassNotFoundException{
		Connection con=mysqlBase.getConnection();
		PreparedStatement save = con.prepareStatement("INSERT INTO borrows(BookID,LibraryCardNumber,ExpirationDate)"
			+ " VALUES("+BookID+","+LibraryCardNumber+",date_add(now(),interval 30 day))");
		save.executeUpdate();	
		mysqlBase.closeConnection();
	}
	
	public ArrayList<Borrows> getBorrowsList() throws ClassNotFoundException, SQLException{
		Connection con=mysqlBase.getConnection();
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
		 Date BorrowDate,ExpirationDate;
		 
		 while(rs.next()){
			Title=rs.getString("Title");
			Author=rs.getString("Author"); 
			ISBN=rs.getString("ISBN");
			Name=rs.getString("FirstName");
			Surname=rs.getString("LastName");
			LibraryCardNumber=rs.getInt("LibraryCardNumber");
			BorrowDate=rs.getDate("BorrowDate");
			ExpirationDate=rs.getDate("ExpirationDate");
			BorrowID=rs.getInt("BorrowID");
			BookID=rs.getInt("BookID");
			Borrows borrows = new Borrows(Title, Author, ISBN, Name, Surname, LibraryCardNumber, 
					BorrowID,BookID,BorrowDate, ExpirationDate);
			borrowsList.add(borrows);
		 }
		mysqlBase.closeConnection();
		return borrowsList;
	}
	
	public void deleteFromBorrows(Integer BorrowID) throws SQLException, ClassNotFoundException{
		Connection con = mysqlBase.getConnection();
		PreparedStatement delete = con.prepareStatement("DELETE FROM borrows WHERE BorrowID="+BorrowID);
		delete.executeUpdate();
		con.close();
	}
	
	public void returnBorrow(Integer BorrowID) throws SQLException, ClassNotFoundException{
		Connection con = mysqlBase.getConnection();
		PreparedStatement ret = con.prepareStatement("UPDATE borrows SET ReturnDate=current_timestamp()"
				+ " WHERE BorrowID="+BorrowID);
		ret.executeUpdate();
		con.close();
	}
	
}
