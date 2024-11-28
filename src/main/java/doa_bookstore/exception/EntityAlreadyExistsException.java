package doa_bookstore.exception;

/**
 * Exception thrown when an attempt is made to add an entity that already exists in the repository.
 */
public class EntityAlreadyExistsException extends Exception {

    private final Class<?> clazz;

    /**
     * Constructs a new {@code EntityAlreadyExistsException} with the specified class and detail message.
     *
     * @param clazz The class of the entity that already exists.
     * @param msg   The detail message, which provides more information about the exception.
     */
    public EntityAlreadyExistsException(Class<?> clazz, String msg) {
        super(msg);
        this.clazz = clazz;
    }

    /**
     * Constructs a new {@code EntityAlreadyExistsException} with a default detail message.
     *
     * @param clazz The class of the entity that already exists.
     */
    public EntityAlreadyExistsException(Class<?> clazz) {
        super(clazz.getSimpleName() + " already exists.");
        this.clazz = clazz;
    }

    /**
     * Returns the class of the entity that caused this exception.
     *
     * @return The class of the existing entity.
     */
    public Class<?> getEntityClass() {
        return clazz;
    }
}
