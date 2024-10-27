package doa_bookstore.controller;

import doa_bookstore.entity.Author;
import doa_bookstore.entity.Book;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.exception.InsufficientUnitsException;
import doa_bookstore.service.AuthorService;
import doa_bookstore.service.BookService;
import doa_bookstore.service.OrderService;

import java.util.HashMap;
import java.util.List;

/**
 * Controller class for managing bookstore operations.
 * Provides methods to handle book, author, and order-related functionality.
 */
public class BookstoreController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final OrderService orderService;

    /**
     * Constructs a new {@code BookstoreController} with the specified services.
     *
     * @param bookService   The service for managing books.
     * @param authorService The service for managing authors.
     * @param orderService  The service for managing orders.
     */
    public BookstoreController(BookService bookService, AuthorService authorService, OrderService orderService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.orderService = orderService;
    }

    /**
     * Retrieves all books from the bookstore.
     *
     * @return An iterable collection of all books.
     */
    public Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Saves a new book in the bookstore.
     *
     * @param book The book to be saved.
     * @return The saved book.
     * @throws EntityAlreadyExistsException If the book already exists in the repository.
     * @throws EntityNotFoundException If the author associated with the book is not found in the repository.
     */
    public Book saveBook(Book book) throws EntityAlreadyExistsException, EntityNotFoundException {
        if (book.getAuthor() == null || book.getAuthor().getId() == null ||
                !authorService.findAuthorByID(book.getAuthor().getId()).isPresent()) {
            throw new EntityNotFoundException(Author.class);
        }
        return bookService.saveBook(book);
    }


    /**
     * Finds an author by their ID.
     *
     * @param id The ID of the author to find.
     * @return The found author.
     * @throws EntityNotFoundException If no author with the specified ID is found.
     */
    public Author findAuthorById(Long id) throws EntityNotFoundException {
        return authorService.findAuthorByID(id)
                .orElseThrow(() -> new EntityNotFoundException(Author.class));
    }


    /**
     * Saves a new author in the bookstore.
     *
     * @param author The author to be saved.
     * @return The saved author.
     * @throws EntityAlreadyExistsException If the author already exists in the repository.
     */
    public Author saveAuthor(Author author) throws EntityAlreadyExistsException {
        return authorService.saveAuthor(author);
    }

    /**
     * Retrieves a list of books written by the specified author.
     *
     * @param author The author whose books are to be retrieved.
     * @return A list of books written by the author.
     * @throws EntityNotFoundException If the author does not exist in the repository.
     */
    public List<Book> booksByAuthor(Author author) throws EntityNotFoundException {
        return authorService.findAuthorByID(author.getId())
                .orElseThrow(() -> new EntityNotFoundException(Author.class))
                .getBooks();
    }


    /**
     * Creates an order for the specified customer with a list of books and their quantities.
     *
     * @param customerName The name of the customer placing the order.
     * @param orders       A map of books and their quantities to be ordered.
     * @return {@code true} if the order is successfully created, {@code false} otherwise.
     * @throws InsufficientUnitsException If there are not enough units of any book in the order.
     * @throws EntityNotFoundException    If a book in the order is not found in the repository.
     * @throws EntityAlreadyExistsException If the order already exists in the repository.
     */
    public boolean makeOrder(String customerName, HashMap<Book, Integer> orders)
            throws InsufficientUnitsException, EntityNotFoundException, EntityAlreadyExistsException {
        // Check availability of all books in the order
        for (Book bookFromList : orders.keySet()) {
            Book bookFromRepo = bookService.findBookById(bookFromList.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Book.class));
            if(!bookFromRepo.getTitle().equals(bookFromList.getTitle()))
                throw new EntityNotFoundException(Book.class);
            int requestedQuantity = orders.get(bookFromList);

            if (bookFromRepo.getStockUnits() < requestedQuantity) {
                throw new InsufficientUnitsException();
            }
        }
        // If all checks pass, create the order
        orderService.createOrder(customerName, orders);
        return true;
    }

}
