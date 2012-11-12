/**
 * An InsufficientFundsException is thrown when a BankAccount determines
 * that it does not contain enough funds to perform a transaction (withdrawal).
 * Note that it is a _checked_ exception because it is a subclass of Exception
 * but not a subclass of RuntimeException.
 *
 * @author cs302
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
        super();
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
