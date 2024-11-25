package doa_bookstore.service;

import doa_bookstore.entity.Book;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing books in the bookstore system.
 * Provides business logic for retrieving, saving, and finding books.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    /**
     * Retrieves all books in the repository.
     *
     * @return A list of all books.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Saves a new book to the repository.
     *
     * @param book The book to save.
     * @return The saved book.
     * @throws EntityAlreadyExistsException If a book with the same title already exists.
     */
    public Book saveBook(Book book) throws EntityAlreadyExistsException {
        if (book.getId() != null && bookRepository.existsById(book.getId())) {
            throw new EntityAlreadyExistsException(Book.class);
        }
        if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
            throw new EntityAlreadyExistsException(Book.class);
        }
        return bookRepository.save(book);
    }


    /**
     * Finds a book by its ID.
     *
     * @param id The ID of the book to find.
     * @return An Optional containing the found book, or an empty Optional if the book does not exist.
     */
    public Optional<Book> findBookById(long id) {
        return bookRepository.findById(id);
    }
}
