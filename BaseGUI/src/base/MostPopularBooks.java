package base;

public class MostPopularBooks{

	private String Title, Author;
	private Integer Borrows;
	
	
	public MostPopularBooks() {
		
	}

	public MostPopularBooks(String title, String author, Integer borrows) {
		Title = title;
		Author = author;
		Borrows = borrows;
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

	public Integer getBorrows() {
		return Borrows;
	}

	public void setBorrows(Integer borrows) {
		Borrows = borrows;
	}
	

}
