package doa_bookstore.repository;

import doa_bookstore.entity.Author;
import doa_bookstore.entity.interfaces.Entity;
import doa_bookstore.exception.EntityAlreadyExistsException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 * An abstract base class for a simple CRUD (Create, Read, Update, Delete) repository.
 * This class provides basic CRUD operations using an in-memory data store implemented with a HashMap.
 * It supports generic entity types and ensures that each subclass follows the Singleton pattern.
 *
 * @param <E> The type of entity stored in the repository, which must implement the {@link Entity} interface.
 */
public abstract class MyCrudRepository<E extends Entity<Long>> {



    // Incremental ID counter for new entities
    private long idCounter = 1;

    // In-memory data storage
    private final HashMap<Long, E> table = new HashMap<>();

    // Private constructor to prevent external instantiation
    protected MyCrudRepository() {
    }

    /**
     * Saves an entity to the repository and assigns it a unique ID if it is a new entity.
     * If the entity already exists, an {@link EntityAlreadyExistsException} is thrown.
     *
     * @param entity The entity to be saved.
     * @return The saved entity.
     * @throws EntityAlreadyExistsException If an entity with the same ID already exists in the repository.
     */
    public E save(E entity) throws EntityAlreadyExistsException {
        if (entity.getId() != null && existsById(entity.getId())) {
            throw new EntityAlreadyExistsException(entity.getClass());
        }
        if (entity.getId() == null) {
            entity.setId(idCounter++);
        }
        table.put(entity.getId(), entity);
        return entity;
    }

    /**
     * Finds an entity in the repository by its ID.
     *
     * @param id The ID of the entity to find.
     * @return An {@link Optional} containing the entity if found, or an empty {@link Optional} if not found.
     */
    public Optional<E> findById(Long id) {
        return Optional.ofNullable(table.get(id));
    }

    /**
     * Deletes an entity from the repository by its ID.
     *
     * @param id The ID of the entity to delete.
     */
    public void deleteById(Long id) {
        table.remove(id);
    }

    /**
     * Checks whether an entity exists in the repository by its ID.
     *
     * @param id The ID of the entity to check.
     * @return {@code true} if the entity exists, {@code false} otherwise.
     */
    public boolean existsById(Long id) {
        return table.containsKey(id);
    }

    /**
     * Retrieves all entities from the repository.
     *
     * @return A list of all entities in the repository.
     */
    public List<E> findAll() {
        return new ArrayList<>(table.values());
    }

    /**
     * Deletes all entities from the repository.
     */
    public void deleteAll() {
        table.clear();
    }

    public E update(E entity) {
        return table.replace(entity.getId(),entity);
    }
}
