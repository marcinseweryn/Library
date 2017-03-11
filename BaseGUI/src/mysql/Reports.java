package mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import base.MostPopularBooks;
import base.StandardReport;

public class Reports {
	
	
	
	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
	
	public ArrayList<StandardReport> standardReport(Date dateFrom, Date dateTo) throws ClassNotFoundException, SQLException{
		Connection con = connectionToDatabase.getConnection(); 
		PreparedStatement get = con.prepareStatement("SELECT d.Date , count(distinct b.BorrowID) as BorrowedBooks,"
				+ " count(distinct r.ReservationID) as BookedBooks,"
				+ " count(distinct b.ReturnDate) as ReturnedBooks, count(distinct u.LibraryCardNumber) as NewUsers"
					+ "	FROM dates as d "
					+ "	LEFT JOIN borrows as b ON d.Date=DATE(b.BorrowDate)"
					+ "	LEFT JOIN reservations as r ON d.Date=DATE(r.ReservationDate)"
					+ "	LEFT JOIN users as u ON d.Date=DATE(u.RegistrationDate)"
						+ "	WHERE d.Date BETWEEN '"+dateFrom+"' AND '"+dateTo+"'"
						+ "	Group by d.Date");
		ResultSet rs = get.executeQuery();
		
		ArrayList<StandardReport> reportList = new ArrayList<>();
		Integer BorrowedBooks,BookedBooks,ReturnedBooks,NewUsers,
		sumBorrowedBooks = 0,sumBookedBooks = 0,sumReturnedBooks = 0,sumNewUsers = 0;
		Date Date;
		while(rs.next()){
			BorrowedBooks = rs.getInt("BorrowedBooks");
			BookedBooks = rs.getInt("BookedBooks");
			ReturnedBooks = rs.getInt("ReturnedBooks");
			NewUsers = rs.getInt("NewUsers");
			Date = rs.getDate("Date");
			
			sumBorrowedBooks += BorrowedBooks;
			sumBookedBooks += BookedBooks;
			sumReturnedBooks += ReturnedBooks;
			sumNewUsers += NewUsers;
			
			StandardReport standardReport = new StandardReport(BorrowedBooks, BookedBooks,
					ReturnedBooks, NewUsers, Date.toString());
			reportList.add(standardReport);
		}
		StandardReport standardReport = new StandardReport(sumBorrowedBooks, sumBookedBooks,
				sumReturnedBooks, sumNewUsers, "Sum");
		reportList.add(standardReport);
		return reportList;
	}
	
	public ArrayList<MostPopularBooks> mostPopularBooksReport(Date dateFrom, Date dateTo) throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection(); 
		PreparedStatement get = con.prepareStatement("SELECT bo.Title,bo.Author, count(distinct b.BorrowID) as Borrows"
				+ " FROM dates as d"
				+ " LEFT JOIN borrows as b ON d.Date=DATE(b.BorrowDate)"
				+ " JOIN books as bo ON b.BookID=bo.BookID"
				+ " WHERE d.Date BETWEEN '2017-03-10' AND '2017-03-11'"
				+ " GROUP BY bo.Title"
				+ " ORDER BY Borrows DESC");
		ResultSet rs = get.executeQuery();
		
		ArrayList<MostPopularBooks> reportList = new ArrayList<>();
		String Title, Author;
		Integer Borrows;
		while(rs.next()){
			Title = rs.getString("Title");
			Author  = rs.getString("Author");
			Borrows = rs.getInt("Borrows");
			
			MostPopularBooks mostPopularBooks = new MostPopularBooks(Title, Author, Borrows);
			reportList.add(mostPopularBooks);
		}
		return reportList;
	}

}
