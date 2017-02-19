package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BorrowsTable {

	MysqlBase mysqlBase = new MysqlBase();
	
	public void seveToBorrows(Integer BookID,Integer LibraryCardNumber ) throws SQLException, ClassNotFoundException{
		Connection con=mysqlBase.getConnection();
		PreparedStatement save = con.prepareStatement("INSERT INTO borrows(BookID,LibraryCardNumber,ExpirationDate)"
			+ " VALUES("+BookID+","+LibraryCardNumber+",date_add(now(),interval 30 day))");
		save.executeUpdate();	
		mysqlBase.closeConnection();
	}
}
