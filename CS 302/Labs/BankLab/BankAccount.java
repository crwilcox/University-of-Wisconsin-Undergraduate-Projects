/**
 * A class to represent a single bank account.
 * @author cs302
 */
public class BankAccount {

    private double balance;
    private int accountNumber;

    /**
     * Constructor
     *
     * Constructs a BankAccount with a zero balance
     */
    public BankAccount() {
        this.accountNumber = 0;
        this.balance = 0;
    }

    /**
     * Constructor
     *
     * @param intialBalance the initial balance of the BankAccount
     */
    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                    "Don't create accounts with a negative balance!");
        }
        balance = initialBalance;
    }

    /**
     * New constructor (task 3)
     * @param initialBalance the initial balance of the BankAccount
     * @param accountNumber The account number to associate with this 
     *                      BankAccount.  Must be a 5 digit integer.
     */
    public BankAccount(double initialBalance, int accountNumber) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                    "Don't create accounts with a negative balance!");
        }
        if (accountNumber < 10000 || accountNumber > 99999) {
            throw new IllegalArgumentException(
                    "Account numbers must be 5 digits and positive.");
        }
        balance = initialBalance;
        this.accountNumber = accountNumber;
    }

    /**
     * Deposits money into the BankAccount
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Don't deposit negative amounts!");
        }
        balance = balance + amount;
    }

    /**
     * Withdraws money from the BankAccount
     *
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Don't withdraw too much!");
        }
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Don't withdraw a negative amount!");
        }
        balance = balance - amount;
    }

    /**
     * Gets the current balance of the BankAccount
     *
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the account number of the BankAccount
     * @return the account number
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Returns a string representation this BankAccount in
     * the following format, e.g. 12345 $100.52 where 12345
     * is the account number, and 100.52 is the balance.
     */
    public String toString () {
        return "" + accountNumber + " $" + balance;
    }
}
