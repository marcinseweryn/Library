package base;
import java.io.Serializable;

public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title,ISBN,author;
	private int ID;
	

public Book(){
	
}
public Book(String mark,String power,String price)
{
	this.title=mark;
	this.author=power;
	this.ISBN=price;
}
public Book(int ID,String mark,String power,String price)
{
	this.ID=ID;
	this.title=mark;
	this.author=power;
	this.ISBN=price;
}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		ID = ID;
	}
	
	@Override
	public String toString()
	{
		return "Title: "+title+" Author: "+author+" ISBN: "+ISBN;
	}
}
