package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import base.Users;

public class UsersTable {
	
	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
	
	public ArrayList<Users> getUserTable() throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement getUsers=con.prepareStatement("SELECT u.LibraryCardNumber,u.FirstName,"
				+ "u.LastName,u.City,u.Address,u.PostalCode,u.Telephone,u.Email,u.Banned,"
				+ "count(case when b.ReturnDate is null and b.BorrowID is not null then 1 else null end) as Borrows,"
				+ "count(case when b.ExpirationDate < current_date() and b.ReturnDate is null then 1 else null end) as ExpirationDates "
				+ "FROM users as u "
					+ "LEFT OUTER JOIN borrows as b on u.LibraryCardNumber=b.LibraryCardNumber "
					+ "GROUP BY u.LibraryCardNumber");
		ResultSet rs=getUsers.executeQuery();
		
		ArrayList<Users> usersList=new ArrayList<>();
		Integer LibraryCardNumber,Borrows,ExpirationDates;
		String Name,Surname,City,Address,PostalCode,Telephone,Email,Banned;
		
		while(rs.next()){
			
			LibraryCardNumber = rs.getInt("LibraryCardNumber");
			Borrows = rs.getInt("Borrows");
			ExpirationDates = rs.getInt("ExpirationDates");
			Name = rs.getString("FirstName");
			Surname = rs.getString("LastName");
			City = rs.getString("City");
			Address = rs.getString("Address");
			PostalCode = rs.getString("PostalCode");
			Telephone = rs.getString("Telephone");
			Email = rs.getString("Email");
			Banned = rs.getString("Banned");
			
			Users users = new Users(LibraryCardNumber, Borrows, ExpirationDates, Name, Surname,
				City, Address, PostalCode, Telephone, Email, Banned);	
			
			usersList.add(users);
		}
		con.close();
		return usersList;
	}
	
	public void changeUserPassword(Integer LibraryCardNumber,String password) throws ClassNotFoundException, SQLException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement pass = con.prepareStatement("UPDATE users SET Password='"+password+"' "
				+ "WHERE LibraryCardNumber="+LibraryCardNumber);
		pass.executeUpdate();
		con.close();
	}
	
	public void deleteUser(Integer LibraryCardNumber) throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement del = con.prepareStatement("DELETE FROM users "
				+ "WHERE LibraryCardNumber="+LibraryCardNumber);
		del.executeUpdate();
		con.close();
		
	}
	
	public Users getUserSettings(Integer LibraryCardNumber) throws SQLException, ClassNotFoundException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement get = con.prepareStatement("Select LibraryCardNumber,FirstName,LastName,"
				+ "City,Address,PostalCode,Telephone,Email,Password "
					+ "FROM users WHERE LibraryCardNumber="+LibraryCardNumber);		
		ResultSet rs = get.executeQuery();
		
		String Name,Surname,City,Address,PostalCode,Telephone,Email,Password;
	
			rs.next();
			Name = rs.getString("FirstName");
			Surname = rs.getString("LastName");
			City = rs.getString("City");
			Address = rs.getString("Address");
			PostalCode = rs.getString("PostalCode");
			Telephone = rs.getString("Telephone");
			Email = rs.getString("Email");
			Password = rs.getString("Password");
			
		Users  user = new Users(Name, Surname, City, Address, PostalCode, Telephone, Email, Password);	
		
		con.close();
		return user;		
	}
	
	public void setUserSettings(Integer LibraryCardNumber, String Name, String Surname, String City, String Address,
			String PostalCode, String Telephone, String Email, String Password) throws SQLException, ClassNotFoundException{
		
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement pass = con.prepareStatement("UPDATE users SET FirstName='"+Name+"',"
				+ "LastName='"+Surname+"',City='"+City+"',Address='"+Address+"',PostalCode='"+PostalCode+"',"
						+ "Telephone='"+Telephone+"',Email='"+Email+"',Password='"+Password+"'"
						+ " WHERE LibraryCardNumber="+LibraryCardNumber);
		pass.executeUpdate();
		con.close();
		
	}

	
	
}
