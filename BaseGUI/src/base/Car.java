package base;
import java.io.Serializable;

public class Car implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mark,price,power;
	
public Car(){
	
}
public Car(String mark,String power,String price)
{
	this.mark=mark;
	this.power=power;
	this.price=price;
}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
	@Override
	public String toString()
	{
		return "Marka: "+mark+" Moc: "+power+" Cena: "+price;
	}
}
