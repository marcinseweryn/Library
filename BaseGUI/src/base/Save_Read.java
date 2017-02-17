package base;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import controllers.admin.BooksOfflineWindowController;
public class Save_Read{
	
private static ArrayList<Book> base;

public void saveList(String fileName) throws IOException{
	FileOutputStream fos=new FileOutputStream(fileName);
	ObjectOutputStream oos=new ObjectOutputStream(fos);
	BooksOfflineWindowController add=new BooksOfflineWindowController();
	oos.writeObject(add.getBase());
	oos.flush();
	oos.close();
}

public void readList(String baseName) throws IOException, ClassNotFoundException
{
	
	FileInputStream fis=new FileInputStream(baseName);
	ObjectInputStream ois=new ObjectInputStream(fis);
	base=(ArrayList<Book>) ois.readObject();
	ois.close();
}



public ArrayList<Book> getBase(String baseName) throws IOException, ClassNotFoundException{
	FileInputStream fis=new FileInputStream(baseName);
	ObjectInputStream ois=new ObjectInputStream(fis);
	base=(ArrayList<Book>) ois.readObject();
	ois.close();
	return base;
}

public String getBaseName() throws FileNotFoundException, IOException, ClassNotFoundException {
	FileInputStream fis=new FileInputStream("lastBaseInfo");
	DataInputStream dis=new DataInputStream(fis);
	String name=dis.readUTF();
	dis.close();
	return name;
}
	
}
