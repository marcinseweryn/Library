package base;

public class StandardReport {

	private Integer BorrowedBooks,BookedBooks,ReturnedBooks,NewUsers;
	private String Date;
		

	public StandardReport() {

	}
	
	public StandardReport(Integer borrowedBooks, Integer bookedBooks, Integer returnedBooks, Integer newUsers,
			String date) {
		super();
		BorrowedBooks = borrowedBooks;
		BookedBooks = bookedBooks;
		ReturnedBooks = returnedBooks;
		NewUsers = newUsers;
		Date = date;
	}



	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Integer getBorrowedBooks() {
		return BorrowedBooks;
	}

	public void setBorrowedBooks(Integer borrowedBooks) {
		BorrowedBooks = borrowedBooks;
	}

	public Integer getBookedBooks() {
		return BookedBooks;
	}

	public void setBookedBooks(Integer bookedBooks) {
		BookedBooks = bookedBooks;
	}

	public Integer getReturnedBooks() {
		return ReturnedBooks;
	}

	public void setReturnedBooks(Integer returnedBooks) {
		ReturnedBooks = returnedBooks;
	}

	public Integer getNewUsers() {
		return NewUsers;
	}

	public void setNewUsers(Integer newUsers) {
		NewUsers = newUsers;
	}
	
}
