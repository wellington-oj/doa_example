package doa_bookstore.service;

import doa_bookstore.entity.Author;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service class for managing authors in the bookstore system.
 * Provides business logic for retrieving, saving, and finding authors.
 */
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Saves a new author to the repository.
     *
     * @param author The author to be saved.
     * @return The saved author.
     * @throws EntityAlreadyExistsException If an author with the same ID already exists in the repository.
     */
    public Author saveAuthor(Author author) throws EntityAlreadyExistsException {
        return authorRepository.save(author);
    }

    /**
     * Finds an author by their ID.
     *
     * @param authorID The ID of the author to find.
     * @return An {@code Optional<Author>} containing the found author if present, or an empty {@code Optional} if not found.
     */
    public Optional<Author> findAuthorByID(Long authorID) {
        return authorRepository.findById(authorID);
    }


    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }
}
