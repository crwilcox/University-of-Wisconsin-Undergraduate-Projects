import java.util.Scanner;

public class PairTester{

	public static void main (String[] args){
	
	Scanner scan1 = new Scanner(System.in);
	System.out.println("Enter the First Number:");
	double point1 = scan1.nextDouble();
	System.out.println("Enter the Second Number:");
	double point2 = scan1.nextDouble();
	
	Pair pair1 = new Pair(point1, point2);
	
	System.out.println("The Sum is: " + pair1.getSum());
	
	System.out.println("The Average is: " + pair1.getAvg());
	
	System.out.println("The Maximum is: " + pair1.getMax());
	
	}
}