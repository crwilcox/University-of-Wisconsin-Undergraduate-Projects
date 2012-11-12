
public class Vehicle {
	private int passengers;
	private double price;
	
	public Vehicle(double p, int cap){
		price=p;
		passengers=cap;
	}

	public double returnPrice(){
		return price;
	}
	
	public int returnPassengers(){
		return passengers;
	}
	
	public void changePrice(double newPrice){
		price=newPrice;
	}
	
	public String toString(){
		String newString="$"+price+", "+passengers+"-passenger";
		return newString;
	}
}
