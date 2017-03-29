package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import base.Ban;

public class Bans {
	
	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();

	public void banUser(Integer LibraryCardNumber, String Reason, Integer Days) throws SQLException, ClassNotFoundException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement save = con.prepareStatement("INSERT INTO banned_users(LibraryCardNumber,ExpirationDate,Reason) "+
				"VALUES("+LibraryCardNumber+",date_add(now(),interval "+Days+" day),'"+Reason+"')");
		save.executeUpdate();
		
		PreparedStatement update = con.prepareStatement("UPDATE users SET Banned='Yes' "
				+ "WHERE LibraryCardNumber="+LibraryCardNumber);
		update.executeUpdate();	
		
		con.close();
	}
	
	
	public void unbanUser(Integer LibraryCardNumber) throws ClassNotFoundException, SQLException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement delete = con.prepareStatement("DELETE FROM banned_users WHERE "
				+ "LibraryCardNumber="+LibraryCardNumber);
		delete.executeUpdate();
		
		PreparedStatement update = con.prepareStatement("UPDATE users SET Banned='No' "
				+ "WHERE LibraryCardNumber="+LibraryCardNumber);
		update.executeUpdate();	
		
		con.close();
		
	}
	
	public boolean checkBannedUsers(Integer LibraryCardNumber) throws SQLException, ClassNotFoundException
	{
		boolean banned;	
		Integer bans;
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement get = con.prepareStatement("SELECT count(LibraryCardNumber) as bans FROM banned_users"
				+ " WHERE LibraryCardNumber="+LibraryCardNumber);
		ResultSet rs=get.executeQuery();
		
		rs.next();
		bans = rs.getInt("bans");
		
		if(bans>0){
			banned = true;
			System.out.println("1");
		}else{
			banned = false;
			System.out.println("2");
		}
		
		con.close();
		return banned;
	}
	
	public Ban banInformation(Integer LibraryCardNumber) throws SQLException, ClassNotFoundException{
		
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement get = con.prepareStatement("SELECT reason,BanDate,ExpirationDate FROM banned_users "
				+ "WHERE LibraryCardNumber="+LibraryCardNumber);
		ResultSet rs=get.executeQuery();
		
		String Reason;
		Timestamp BanDate,ExpirationDate;
		
		rs.next();
		Reason = rs.getString("reason");
		BanDate = rs.getTimestamp("BanDate");
		ExpirationDate = rs.getTimestamp("ExpirationDate");
		
		@SuppressWarnings("deprecation")
		Ban ban = new Ban(LibraryCardNumber, Reason, BanDate.toGMTString(), ExpirationDate.toGMTString());
		
		return ban;
	}
	
	public void deleteExpiretBans() throws ClassNotFoundException, SQLException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement update = con.prepareStatement("UPDATE users SET Banned='No' "
				+ "WHERE LibraryCardNumber in(SELECT LibraryCardNumber FROM banned_users "
					+ "WHERE ExpirationDate<current_timestamp())");
		update.executeUpdate();	
		
		PreparedStatement delete = con.prepareStatement("DELETE FROM banned_users "
				+ "WHERE ExpirationDate<current_timestamp()");
		delete.executeUpdate();
	}
	
	public void automaticBansForBorrowing() throws SQLException, ClassNotFoundException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement get = con.prepareStatement("SELECT u.LibraryCardNumber,"
				+ "count(case when b.ExpirationDate < current_date() and"
				+ " b.ReturnDate is null then 1 else null end) as expirations "
					+"FROM users as u "
					+"JOIN borrows as b ON b.LibraryCardNumber=u.LibraryCardNumber "
					+"WHERE u.banned='No' "
					+"HAVING expirations > 2");
		ResultSet rs=get.executeQuery();
		
		Integer LibraryCardNumber;
		while(rs.next()){
			LibraryCardNumber = rs.getInt("LibraryCardNumber");
			banUser(LibraryCardNumber,"You have three or more not returned books."
					+ " You must return the books to the library to unlock the account.",4000);
		}
		
	}
	
	public void automaticBansForReservations() throws SQLException, ClassNotFoundException{
		Connection con=connectionToDatabase.getConnection();
		PreparedStatement get = con.prepareStatement("SELECT u.LibraryCardNumber,"
				+ "count(case when r.Completed='No' then 1 else null end) as expirations,"
				+ "count(case when r.Completed='Cancelled' then 1 else null end) as Cancelled "
					+"FROM users as u "
					+"JOIN reservations as r ON r.LibraryCardNumber=u.LibraryCardNumber "
					+"WHERE u.banned='No' and (r.ReservationDate between "
					+ "(current_timestamp()- interval 30 day) and current_timestamp()) "
					+ "GROUP BY u.LibraryCardNumber");
		ResultSet rs=get.executeQuery();
		Integer LibraryCardNumber,Expirations,Cancelled;
		while(rs.next()){
			LibraryCardNumber = rs.getInt("LibraryCardNumber");
			Expirations = rs.getInt("expirations");
			Cancelled = rs.getInt("Cancelled");
			System.out.println(Cancelled);
			if(Expirations>=3){
				banUser(LibraryCardNumber,"You have three or more not realized books reservations "
						+ "in last 30 days.",14);
			}else{
				if((Cancelled+Expirations)>=5){
					banUser(LibraryCardNumber,"You have five or more not realized or cancelled books reservations "
							+ "in last 30 days.",14);
				}else{
					
					}					
				}
		}	
	}
	
	
}
