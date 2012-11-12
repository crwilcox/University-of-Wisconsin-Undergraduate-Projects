import java.util.Scanner;
public class StringOpsTester {
	
	public static void main (String[] args) {
		
		Scanner scan1 = new Scanner(System.in);
		System.out.println("Enter the first string:");
		String line1 = scan1.nextLine();
		System.out.println("Enter the second string:");
		String line2 = scan1.nextLine();

		
		System.out.println("The first word is: " + line1);
		System.out.println("The second word is: " + line2);
		
	 StringOps stringOps = new StringOps(line1, line2);
		
		 System.out.println("This is the FIRST character of the first word: " + stringOps.giveFirstChar());
		 
		 System.out.println("This is the LAST character of the first word: " + stringOps.giveLastChar());
		 
		 System.out.println("This is the MIDDLE character of the first word: " + stringOps.giveMidChar());
		 
		 System.out.println("This is the CONCATENATION of the words: " + stringOps.giveConcat());
		
		}
	}