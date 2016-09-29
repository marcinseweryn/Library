package mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.prism.ResourceFactoryListener;

import base.Car;
import javafx.scene.control.TableView;

public class MysqlBase {

	private Connection con = null;
	

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/base"
				+ "?autoReconnect=true&useSSL=false","root","1234");
		System.out.println("connect");
		
		return con;
	}

	public void closeConnection() throws SQLException{
		con.close();
		System.out.println("close");
	}
	
	public ArrayList<Car> getMysqlBase() throws SQLException, ClassNotFoundException{
		Connection con=getConnection();
		PreparedStatement getbase=con.prepareStatement("SELECT ID,Marka,Moc,Cena FROM base"
				+ " ORDER BY 'ID' ASC");
		ResultSet rs=getbase.executeQuery();
		
		ArrayList<Car> list=new ArrayList<Car>();
		String mark,power,price;
		int ID;
		while(rs.next()){
			ID=rs.getInt("ID");
			mark=rs.getString("Marka");
			power=rs.getString("Moc");
			price=rs.getString("Cena");
			Car car=new Car(ID,mark,power,price);
			list.add(car);
		}
		return list;		
	}
	
	public void deleteFromMysqlBase(int ID) throws ClassNotFoundException, SQLException
	{
		System.out.println(ID);
		Connection con=getConnection();
		PreparedStatement delete=con.prepareStatement("DELETE FROM base "
				+ "WHERE ID="+ID+"");
		delete.executeUpdate();	
	}
	
	public void saveToMysqlBase(Car car) throws ClassNotFoundException, SQLException{
		Connection con=getConnection();
		PreparedStatement save=con.prepareStatement("INSERT INTO base "
				+ "VALUES('"+car.getID()+"','"+car.getMark()+"','"+car.getPower()
				+"','"+car.getPrice()+"')");
		save.executeUpdate();				
	}
	
	public void updateMysqlBaseRecord(int ID,String mark,String power,String price) 
			throws ClassNotFoundException, SQLException{
		Connection con=getConnection();
		PreparedStatement update = con.prepareStatement("UPDATE base "
				+ "SET Marka='"+mark+"',Moc='"+power+"',Cena='"+price
				+ "' WHERE ID="+ID);
		update.executeUpdate();
	}
	
	
	public void createTable() throws ClassNotFoundException, SQLException{
		try{
		Connection con=getConnection();
		PreparedStatement create=con.prepareStatement("CREATE TABLE IF NOT EXISTS base"
				+ "(ID int,Marka varchar(20),Moc varchar(20),Cena varchar(20),PRIMARY KEY(ID))"
				+ "");
		create.executeUpdate();
		}catch(Exception e){System.out.println(e);}
	}
		
		
}
