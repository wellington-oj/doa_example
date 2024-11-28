package doa_bookstore.service;

import doa_bookstore.entity.Author;
import doa_bookstore.entity.Book;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing authors in the bookstore system.
 * Provides business logic for retrieving, saving, and finding authors.
 */
@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    /**
     * Saves a new author to the repository.
     *
     * @param author The author to be saved.
     * @return The saved author.
     * @throws EntityAlreadyExistsException If an author with the same name already exists.
     */
    public Author saveAuthor(Author author) throws EntityAlreadyExistsException {
        if (author.getId() != null && authorRepository.existsById(author.getId())) {
            throw new EntityAlreadyExistsException(Author.class);
        }
        if (authorRepository.findByName(author.getName()).isPresent()) {
            throw new EntityAlreadyExistsException(Author.class);
        }
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

    /**
     * Updates an existing author in the repository.
     *
     * @param author The author to be updated.
     * @return The updated author.
     * @throws EntityNotFoundException If the author does not exist in the repository.
     */
    public Author updateAuthor(Author author) throws EntityNotFoundException {
        if (!authorRepository.existsById(author.getId())) {
            throw new EntityNotFoundException(Author.class);
        }
        return authorRepository.save(author);
    }

    /**
     * Retrieves all authors in the repository.
     *
     * @return A list of all authors.
     */
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
