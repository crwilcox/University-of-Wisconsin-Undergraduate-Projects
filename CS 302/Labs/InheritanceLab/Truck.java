
public class Truck extends Vehicle{

	private int cargoCapacity;

	
	public Truck(int cargo, double price){
		super(price, 2);
		cargoCapacity=cargo;
	}
	
	public String toString(){
		return "Truck: "+super.toString()+", "+cargoCapacity+" cu ft";

		
	}
	
	
}
