package base;

import java.sql.Date;

public class Borrows {
	
	private String Title,Author,ISBN,Name,Surname;
	private Integer LibraryCardNumber,BookID,BorrowID;
	private Date BorrowDate,ExpirationDate;
	
	public Borrows(String title, String author, String iSBN, String name, String surname, Integer libraryCardNumber,
			Integer borrowID, Date borrowDate, Date expirationDate) {
		super();
		Title = title;
		Author = author;
		ISBN = iSBN;
		Name = name;
		Surname = surname;
		LibraryCardNumber = libraryCardNumber;
		BorrowID = borrowID;
		BorrowDate = borrowDate;
		ExpirationDate = expirationDate;
	}

	public Borrows() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}
	public Integer getLibraryCardNumber() {
		return LibraryCardNumber;
	}
	public void setLibraryCardNumber(Integer libraryCardNumber) {
		LibraryCardNumber = libraryCardNumber;
	}
	public Integer getBookID() {
		return BookID;
	}
	public void setBookID(Integer bookID) {
		BookID = bookID;
	}
	public Integer getBorrowID() {
		return BorrowID;
	}

	public void setBorrowID(Integer borrowID) {
		BorrowID = borrowID;
	}	
	public Date getBorrowDate() {
		return BorrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		BorrowDate = borrowDate;
	}
	public Date getExpirationDate() {
		return ExpirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		ExpirationDate = expirationDate;
	}

}
