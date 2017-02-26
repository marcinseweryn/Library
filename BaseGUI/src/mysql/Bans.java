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
		PreparedStatement get = con.prepareStatement("SELECT count(LibraryCardNumber) as bans FROM banned_users");
		ResultSet rs=get.executeQuery();
		
		rs.next();
		bans = rs.getInt("bans");
		if(bans>0){
			banned = true;
		}else{
			banned = false;
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
	
	
}
