package base;
import java.io.Serializable;

public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title,ISBN,author,available;

	private int BookID;
	

public Book(){
	
}

public Book(String title,String author,String ISBN,String available )
{
	this.title=title;
	this.author=author;
	this.ISBN=ISBN;
	this.available=available;
}

public Book(int ID,String title,String author,String ISBN,String available )
{
	this.BookID=ID;
	this.title=title;
	this.author=author;
	this.ISBN=ISBN;
	this.available=available;
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
	
	public int getBookID() {
		return BookID;
	}
	public void setBookID(int BookID) {
		this.BookID = BookID;
	}
	
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	
	@Override
	public String toString()
	{
		return "Title: "+title+" Author: "+author+" ISBN: "+ISBN;
	}
}
