package doa_bookstore.exception;

/**
 * Exception thrown when an attempt is sell a book but there are not enough units in the stock
 */
public class InsufficientUnitsException extends Exception {

    /**
     * Constructs a new {@code InsuficientUnitsException} with the specified detail message.
     *
     * @param msg The detail message, which provides more information about the exception.
     */
    public InsufficientUnitsException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new {@code InsuficientUnitsException} with no detail message.
     */
    public InsufficientUnitsException() {
        super("Insufficient Books.");
    }
}
