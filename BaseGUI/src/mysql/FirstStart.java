package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FirstStart {

	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
	
	public void CreateTables() throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		
		///////////////USERS////////////////////////////////////////////////////////////////
		PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Users(" +
					"LibraryCardNumber INT auto_increment primary key, "+
					"FirstName varchar(20),"+
					"LastName varchar(20),"+
					"City varchar(30),"+
					"Address varchar(30),"+
					"PostalCode varchar (10),"+
					"Telephone varchar(20),"+
					"Email varchar(30),"+
					"Password varchar(40),"+
					"Banned varchar(3) default 'No',"+
					"RegistrationDate datetime default current_timestamp"+
					")");
		create.executeUpdate();
		////////////////////////////////////////////////////////////////////////////////////
		
		
		
		/////////////BOOKS/////////////////////////////////////////////////////////////////
		 create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Books("+
					"BookID INT NOT NULL auto_increment,"+
					"Title varchar(100),"+
					"Author varchar(100),"+
					"ISBN varchar(100),"+
					"Available varchar(3) default 'Yes',"+
					"PRIMARY KEY(BookID)"+
					")");
		create.executeUpdate();
		////////////////////////////////////////////////////////////////////////////////////
		
		
		
		///////////BORROWS////////////////////////////////////////////////////////////////
		 create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Borrows("+
					"BorrowID INT NOT NULL auto_increment,"+
					"BookID INT NOT NULL,"+
					"LibraryCardNumber INT NOT NULL,"+
					"BorrowDate timestamp default CURRENT_TIMESTAMP,"+
					"ExpirationDate timestamp default current_timestamp,"+
					"ReturnDate timestamp,"+
					"PRIMARY KEY(BorrowID)"+
					")");
		create.executeUpdate();
		/////////////////////////////////////////////////////////////////////////////////
		
		
		
		/////////RESERVATIONS////////////////////////////////////////////////////////////
		 create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Reservations("+
					"ReservationID INT NOT NULL auto_increment,"+
					"BookID INT NOT NULL,"+
					"LibraryCardNumber INT NOT NULL,"+
					"ReservationDate timestamp default CURRENT_TIMESTAMP,"+
					"ExpirationDate timestamp default current_timestamp,"+
					"Completed varchar(10),"+
					"PRIMARY KEY(ReservationID)"+
					")");
		create.executeUpdate();
		////////////////////////////////////////////////////////////////////////////////////
		
		
		
		/////////BANNED USERS////////////////////////////////////////////////////////////
		 create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Banned_Users("+
					"LibraryCardNumber INT NOT NULL,"+
					"BanDate timestamp default CURRENT_TIMESTAMP,"+
					"ExpirationDate timestamp default CURRENT_TIMESTAMP,"+
					"Reason VARCHAR(500),"+
					"PRIMARY KEY(LibraryCardNumber)"+
					")");
		create.executeUpdate();
		////////////////////////////////////////////////////////////////////////////////////
		
		
		//////DATES/////////////////////////////////////////////////////////////////////////
		 create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Dates("+
				 	"Date date PRIMARY KEY)");
		 create.executeUpdate();
		////////////////////////////////////////////////////////////////////////////////////
		con.close();
		
	}
}
