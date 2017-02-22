package base;

public class Users {
	
	private Integer LibraryCardNumber,Borrows,ExpirationDates;
	private String Name,Surname,City,Address,PostalCode,Telephone,Email,Banned;
	
	
	public Users() {
		
	}
	public Users(Integer libraryCardNumber, Integer borrows, Integer expirationDates, String name, String surname,
			String city, String address, String postalCode, String telephone, String email, String banned) {
		super();
		LibraryCardNumber = libraryCardNumber;
		Borrows = borrows;
		ExpirationDates = expirationDates;
		Name = name;
		Surname = surname;
		City = city;
		Address = address;
		PostalCode = postalCode;
		Telephone = telephone;
		Email = email;
		Banned = banned;
	}
	public Integer getLibraryCardNumber() {
		return LibraryCardNumber;
	}
	public void setLibraryCardNumber(Integer libraryCardNumber) {
		LibraryCardNumber = libraryCardNumber;
	}
	public Integer getBorrows() {
		return Borrows;
	}
	public void setBorrows(Integer borrows) {
		Borrows = borrows;
	}
	public Integer getExpirationDates() {
		return ExpirationDates;
	}
	public void setExpirationDates(Integer expirationDates) {
		ExpirationDates = expirationDates;
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
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPostalCode() {
		return PostalCode;
	}
	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getBanned() {
		return Banned;
	}
	public void setBanned(String banned) {
		Banned = banned;
	}

}
