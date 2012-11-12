
public class Car extends Vehicle{

	private boolean automatic;

	
	public Car(boolean auto, int cap, double price){
		super(price, cap);
		automatic=auto;

	}
	
	public String toString(){
		if(automatic)	return "Car: "+super.toString()+", Automatic";
				return "Car: "+super.toString()+", Manual";
				
	}
	
	
}
