package mysql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;


public class FirstStart {

	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
	
	public void CreateTables() throws SQLException, ClassNotFoundException, FileNotFoundException{
		Connection con =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306"
				+ "?autoReconnect=true&useSSL=false","root","1234");
		
		ScriptRunner runner = new ScriptRunner(con);
		runner.runScript(new BufferedReader(new FileReader("LibraryScript.sql")));

		con.close();
	}
}
