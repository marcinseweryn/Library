package base;


import java.sql.Timestamp;

public class Reservations {

	private Integer ReservationID,BookID,LibraryCardNumber;
	private String Title,Author,ISBN,Name,Surname;
	private String ReservationDate, ExpirationDate;
	
	
	public Reservations(Integer reservationID, Integer bookID, Integer libraryCardNumber, String title, String author,
			String iSBN, String name, String surname, String reservationDate,String expirationDate) {
		super();
		ReservationID = reservationID;
		BookID = bookID;
		LibraryCardNumber = libraryCardNumber;
		Title = title;
		Author = author;
		ISBN = iSBN;
		Name = name;
		Surname = surname;
		ReservationDate = reservationDate;
		ExpirationDate = expirationDate;
	}
	public Reservations() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getReservationID() {
		return ReservationID;
	}
	public void setReservationID(Integer reservationID) {
		ReservationID = reservationID;
	}
	public Integer getBookID() {
		return BookID;
	}
	public void setBookID(Integer bookID) {
		BookID = bookID;
	}
	public Integer getLibraryCardNumber() {
		return LibraryCardNumber;
	}
	public void setLibraryCardNumber(Integer libraryCardNumber) {
		LibraryCardNumber = libraryCardNumber;
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
	public String getReservationDate() {
		return ReservationDate;
	}
	public void setReservationDate(String reservationDate) {
		ReservationDate = reservationDate;
	}
	public String getExpirationDate() {
		return ExpirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		ExpirationDate = expirationDate;
	}
		
}
