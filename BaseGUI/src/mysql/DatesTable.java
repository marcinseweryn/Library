package mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
public class DatesTable {

	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
	
	public void checkAndAddDates() throws ClassNotFoundException, SQLException{
		Connection con = connectionToDatabase.getConnection();
		PreparedStatement get = con.prepareStatement("SELECT date FROM dates");
		ResultSet rs = get.executeQuery();
		
		LocalDate localDate = LocalDate.now();
		Date dateNow = Date.valueOf(localDate);
		Date date = null;
		while(rs.next()){
			date = rs.getDate("date");
		}
		
		
		if(date==null){
			PreparedStatement save = con.prepareStatement("INSERT INTO dates VALUES(current_date())");
			save.executeUpdate();

		}else{
			while(date.compareTo(dateNow)!=0){
				PreparedStatement save = con.prepareStatement("INSERT INTO dates "
						+ "VALUES('"+date+"' + interval 1 day)");
				save.executeUpdate();

				get = con.prepareStatement("SELECT date FROM dates");
				rs = get.executeQuery();
				
				while(rs.next()){
					date = rs.getDate("date");
				}
			}
		}
		
	}
}
