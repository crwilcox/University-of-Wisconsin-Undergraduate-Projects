import java.util.Scanner;
import java.io.*;

/**
 * A class to test the BankAccount class.  Uses a menu-driven system to
 * allow the user to interactively test the BankAccount class.
 * @author cs302
 */
public class BankAccountTester {

    private static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        Bank bank = new Bank(100);
       
        try{
        BankIO.readFile("accounts.txt", bank);
        }catch(IOException ioe){
        	ioe.getMessage();
        }
        
        try{
        	BankIO.readTransactions(bank, "newtran.txt");
        }catch(IOException ioe){
        	ioe.getMessage();
        }
        
        BankAccount myChecking = new BankAccount();
        int choice;
        double amount;
        int accountNumber;
        do {
            choice = getUserChoice();
            switch (choice) {
            case 1:
                amount = getAmount();
                accountNumber = getAccountNumber();
                try {
                    myChecking = new BankAccount(amount, accountNumber);
                    bank.add(myChecking);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Message: " + exception.getMessage());
                }
                break;

            case 2:
                amount = getAmount();
                try {
                    myChecking.deposit(amount);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Message: " + exception.getMessage());
                }
                break;

            case 3:
                amount = getAmount();
                try {
                    myChecking.withdraw(amount);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Message: " + exception.getMessage());
                } catch (InsufficientFundsException exception) {
                    System.out.println("Insufficient funds!: "
                            + exception.getMessage());
                }
                break;
            case 4:
                accountNumber = getAccountNumber();
                BankAccount found = bank.find(accountNumber);
                if (found != null) {
                    myChecking = found;
                } else {
                    System.out.println("Bank account " + accountNumber
                            + " not found");
                }
                break;
            case 5:
                System.out.print(bank);
                break;
            }
            System.out.println("Your account: " + myChecking);
        } while (choice != 0);
        System.out.println("Goodbye!");
    }

    private static int getUserChoice() {
        int choice;
        do {
            choice = -1;
            System.out.println("Menu Options:");
            System.out.println("0) Quit");
            System.out.println("1) Create new (current) account with" +
                               " initial balance");
            System.out.println("2) Deposit to current account");
            System.out.println("3) Withdraw from current account");
            System.out.println("4) Find account");
            System.out.println("5) Print all accounts");

            System.out.print("Enter your choice (0 - 5): ");
            try {
                choice = Integer.parseInt(stdin.nextLine());
            } catch (NumberFormatException exception) {
            }
            if (choice < 0 || choice > 5)
                System.out.println("Invalid choice");
        } while (choice < 0 || choice > 5);
        return choice;
    }

    private static double getAmount() {
        System.out.print("Enter the amount: $ ");
        double amount = -1;
        boolean valid = false;
        do {
            try {
                amount = Double.parseDouble(stdin.nextLine());
                valid = true;
            } catch (NumberFormatException exception) {
                System.out.println("Make sure you enter a valid double!");
            }
        } while (!valid);
        return amount;
    }

    private static int getAccountNumber() {
        System.out.print("Enter the account number: ");
        int amount = -1;
        boolean valid = false;
        do {
            try {
                amount = Integer.parseInt(stdin.nextLine());
                valid = true;
            } catch (NumberFormatException exception) {
                System.out.println("Make sure you enter a valid integer!");
            }
        } while (!valid);
        return amount;
    }
}
