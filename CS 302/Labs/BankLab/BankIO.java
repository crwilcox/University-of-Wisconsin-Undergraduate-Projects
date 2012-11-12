import java.util.*;
import java.io.*;
import java.io.PrintStream;

public class BankIO {

	public static BankAccount read(Scanner in) throws IOException {

		int y;// account number
		double x;// balance
		try {
			y = in.nextInt();
			x = in.nextDouble();
			return new BankAccount(x, y);
		} catch (NumberFormatException nfe) {
			throw new IOException(
					"One of your variables is formatted incorrectly...");
		}

	}

	public static void readFile(String filename, Bank bank) throws IOException {

		try {
			Scanner stdin = new Scanner(new File(filename));

			while (stdin.hasNext()) {

				BankAccount temp = read(stdin);
				bank.add(temp);
			}
		} catch (IOException e) {
			throw new IOException("Error With ReadFile and Bank Account");
		}
	}

	public static void writeFile(Bank bank, String fileName) throws IOException {
		try {
			PrintStream print = new PrintStream(fileName);
			print.print(bank.toString());
		} catch (FileNotFoundException fnfe) {
			throw new IOException(fnfe.getMessage());
		}
	}

	public static void readTransactions(Bank bank, String transactions)
			throws IOException {
		Scanner in =new Scanner(new File( transactions));
		while(in.hasNext()){
			
		String line = in.nextLine();
		String delims = "[ ]+"; // this means that each piece is separated by
								// one or more spaces
		String[] tokens = line.split(delims); // this divides the line into
												// pieces

		int myAccount = Integer.parseInt(tokens[0]);
		double myAmount = Double.parseDouble(tokens[2]);

		if (tokens[1].equals("+")){
			(bank.find(myAccount)).deposit(myAmount);
		}
		if (tokens[1].equals("-")) {
			try {
				(bank.find(myAccount)).withdraw(myAmount);
			} catch (InsufficientFundsException ife) {
				throw new IOException(ife.getMessage());
			}
		}
		}
	}
}
