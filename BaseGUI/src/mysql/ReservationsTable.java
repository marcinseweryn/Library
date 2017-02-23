package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


import base.Reservations;

public class ReservationsTable {
		

	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();

	
	public ArrayList<Reservations> getReservationsTable() throws ClassNotFoundException, SQLException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement get = con.prepareStatement("SELECT r.ReservationID,b.BookID,b.Title,b.Author,b.ISBN,"
				+ "u.LibraryCardNumber,u.FirstName,u.LastName,ReservationDate,r.ExpirationDate "
					+"FROM reservations as r "
					+"JOIN users as u ON u.LibraryCardNumber=r.LibraryCardNumber "
					+"JOIN books as b ON b.BookID=r.BookID");
		ResultSet rs=get.executeQuery();
		
		ArrayList<Reservations> reservationsArrayList = new ArrayList<>();
		Integer ReservationID,BookID,LibraryCardNumber;
		String Title,Author,ISBN,Name,Surname;
		Timestamp ReservationDate, ExpirationDate;
		
		while(rs.next()){
			ReservationID = rs.getInt("ReservationID");
			BookID = rs.getInt("BookID");
			LibraryCardNumber = rs.getInt("LibraryCardNumber");
			Title = rs.getString("Title");
			Author = rs.getString("Author");
			ISBN = rs.getString("ISBN");
			Name = rs.getString("FirstName");
			Surname = rs.getString("LastName");
			ReservationDate = rs.getTimestamp("ReservationDate");
			ExpirationDate = rs.getTimestamp("ExpirationDate");
			
			Reservations reservations = new Reservations(ReservationID, BookID, LibraryCardNumber, Title, Author,
					ISBN, Name, Surname, ReservationDate.toGMTString(),ExpirationDate.toGMTString());
			reservationsArrayList.add(reservations);
		}
		
		con.close();
		return reservationsArrayList;
	}
	
	
	public void saveReservation(Integer LibraryCardNumber, Integer BookID) throws ClassNotFoundException, SQLException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement save = con.prepareStatement("INSERT INTO reservations(BookID,LibraryCardNumber,ExpirationDate)"
			+ " VALUES("+BookID+","+LibraryCardNumber+",date_add(now(),interval 2 day))");
		save.executeUpdate();	
		con.close();
	}
	
	public void deleteFromReservations(Integer ReservationID) throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement delete = con.prepareStatement("DELETE FROM reservations WHERE ReservationID="+ReservationID);
		delete.executeUpdate();
		con.close();
	}
	
	public void deleteExpiredReservations() throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement update = con.prepareStatement("UPDATE books SET Available='Yes' "
				+ "WHERE BookID in"
				+ "(SELECT BookID FROM reservations WHERE ExpirationDate<current_timestamp())");
		update.executeUpdate();
		PreparedStatement delete = con.prepareStatement("DELETE FROM reservations "
				+ "WHERE ExpirationDate<current_timestamp() ");
		delete.executeUpdate();
		con.close();	
	}
	
	

}
