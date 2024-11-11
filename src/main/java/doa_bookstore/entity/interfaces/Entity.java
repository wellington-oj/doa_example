package doa_bookstore.entity.interfaces;

/**
 * A generic interface representing an entity with a unique identifier.
 * This interface defines basic methods for getting and setting the entity's ID.
 *
 * @param <ID> The type of the identifier for the entity.
 */
public interface Entity<ID> {

    /**
     * Gets the unique identifier of the entity.
     *
     * @return The ID of the entity.
     */
    ID getId();

    /**
     * Sets the unique identifier of the entity.
     *
     * @param id The ID to set for the entity.
     */
    void setId(ID id);
}
