package base;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import controllers.DatabaseWindowController;
public class Save_Read{
	
private static ArrayList<Car> base;

public void saveList() throws IOException{
	FileOutputStream fos=new FileOutputStream("base");
	ObjectOutputStream oos=new ObjectOutputStream(fos);
	DatabaseWindowController add=new DatabaseWindowController();
	oos.writeObject(add.getBase());
	oos.flush();
	oos.close();
}

public void readList() throws IOException, ClassNotFoundException
{
	
	FileInputStream fis=new FileInputStream("base");
	ObjectInputStream ois=new ObjectInputStream(fis);
	base=(ArrayList<Car>) ois.readObject();
	ois.close();
}



public ArrayList<Car> getBase() throws IOException, ClassNotFoundException{
	FileInputStream fis=new FileInputStream("base");
	ObjectInputStream ois=new ObjectInputStream(fis);
	base=(ArrayList<Car>) ois.readObject();
	ois.close();
	return base;
}
	
}
