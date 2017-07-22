package mysql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;


public class FirstStart {

	ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();
	
	public void CreateTables() throws SQLException, ClassNotFoundException, FileNotFoundException{
		connectionToDatabase.setDatabaseName("");
		Connection con =  connectionToDatabase.getConnection();
		
		ScriptRunner runner = new ScriptRunner(con);
		runner.runScript(new BufferedReader(new FileReader("LibraryScript.sql")));

		con.close();
	}

}
