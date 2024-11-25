package doa_bookstore.service;

import doa_bookstore.entity.Book;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.repository.BookRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing books in the bookstore system.
 * Provides business logic for retrieving, saving, and finding books.
 */
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Constructs a new {@code BookService} with the specified book repository.
     *
     * @param bookRepository The repository for managing Book entities.
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books in the repository.
     *
     * @return A list of all books.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Saves a book to the repository.
     *
     * @param book The book to save.
     * @return The saved book.
     */
    public Book saveBook(Book book) throws EntityAlreadyExistsException {
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
