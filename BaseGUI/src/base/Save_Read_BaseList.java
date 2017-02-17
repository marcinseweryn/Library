package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import controllers.admin.BooksOfflineWindowController;
import controllers.admin.ReportsListWindowController;

public class Save_Read_BaseList {

	private static ArrayList<String> baseList;

	public void saveList() throws IOException{
		FileOutputStream fos=new FileOutputStream("baseList");
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		ReportsListWindowController add=new ReportsListWindowController();
		oos.writeObject(add.getBaseList());
		oos.flush();
		oos.close();
	}

	public void readList() throws IOException, ClassNotFoundException
	{
		
		FileInputStream fis=new FileInputStream("baseList");
		ObjectInputStream ois=new ObjectInputStream(fis);
		baseList=(ArrayList<String>) ois.readObject();
		ois.close();
	}



	public ArrayList<String> getBase() throws IOException, ClassNotFoundException{
		FileInputStream fis=new FileInputStream("baseList");
		ObjectInputStream ois=new ObjectInputStream(fis);
		baseList=(ArrayList<String>) ois.readObject();
		ois.close();
		return baseList;
	}
}
