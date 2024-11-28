package doa_bookstore.exception;

/**
 * Exception thrown when an attempt is made to get an entity that does not exist.
 */
public class EntityNotFoundException extends Exception {

    private final Class<?> clazz;

    /**
     * Constructs a new {@code EntityNotFoundException} with the specified class and detail message.
     *
     * @param clazz The class of the entity that was not found.
     * @param msg   The detail message, which provides more information about the exception.
     */
    public EntityNotFoundException(Class<?> clazz, String msg) {
        super(msg);
        this.clazz = clazz;
    }

    /**
     * Constructs a new {@code EntityNotFoundException} with a default message.
     *
     * @param clazz The class of the entity that was not found.
     */
    public EntityNotFoundException(Class<?> clazz) {
        super(clazz.getSimpleName()  + " not found.");
        this.clazz = clazz;
    }

    /**
     * Returns the class of the entity that caused this exception.
     *
     * @return The class of the entity that was not found.
     */
    public Class<?> getEntityClass() {
        return clazz;
    }
}
