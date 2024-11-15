package doa_bookstore.controller;

import doa_bookstore.dto.AuthorDTO;
import doa_bookstore.dto.BookDTO;
import doa_bookstore.dto.OrdersDTO;
import doa_bookstore.entity.Author;
import doa_bookstore.entity.Book;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.exception.InsufficientUnitsException;
import doa_bookstore.service.AuthorService;
import doa_bookstore.service.BookService;
import doa_bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookstore")
/**
 * Controller class for managing bookstore operations.
 * Provides methods to handle book, author, and order-related functionality.
 */
public class BookstoreController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final OrderService orderService;

    @Autowired
    public BookstoreController(BookService bookService, AuthorService authorService, OrderService orderService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.orderService = orderService;
    }

    /**
     * Retrieves all books from the bookstore as a list of BookDTOs.
     *
     * @return A ResponseEntity with a list of all books in DTO format.
     */
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks().stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }
    /**
     * Retrieves all authors from the bookstore as a list of AuthorDTOs.
     *
     * @return A ResponseEntity with a list of all authors in DTO format.
     */
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors().stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authors);
    }


    /**
     * Saves a new book in the bookstore and returns it as a BookDTO.
     *
     * @param book The book to be saved.
     * @return A ResponseEntity with the saved book in DTO format.
     * @throws EntityAlreadyExistsException If the book already exists.
     * @throws EntityNotFoundException If the author is not found.
     */
    @PostMapping("/books")
    public ResponseEntity<BookDTO> saveBook(@RequestBody Book book) throws EntityAlreadyExistsException, EntityNotFoundException {
        if (book.getAuthor() == null || book.getAuthor().getId() == null ||
                !authorService.findAuthorByID(book.getAuthor().getId()).isPresent()) {
            throw new EntityNotFoundException(Author.class);
        }
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(new BookDTO(savedBook));
    }

    /**
     * Finds an author by their ID and returns it as an AuthorDTO.
     *
     * @param id The ID of the author.
     * @return A ResponseEntity with the found author in DTO format.
     * @throws EntityNotFoundException If no author with the specified ID is found.
     */
    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable Long id) throws EntityNotFoundException {
        Author author = authorService.findAuthorByID(id)
                .orElseThrow(() -> new EntityNotFoundException(Author.class));
        return ResponseEntity.ok(new AuthorDTO(author));
    }

    /**
     * Saves a new author in the bookstore and returns it as an AuthorDTO.
     *
     * @param author The author to be saved.
     * @return A ResponseEntity with the saved author in DTO format.
     * @throws EntityAlreadyExistsException If the author already exists.
     */
    @PostMapping("/authors")
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody Author author) throws EntityAlreadyExistsException {
        Author savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(new AuthorDTO(savedAuthor));
    }

    /**
     * Retrieves a list of books by the specified author as a list of BookDTOs.
     *
     * @param authorId The ID of the author.
     * @return A ResponseEntity with a list of books by the author in DTO format.
     * @throws EntityNotFoundException If the author does not exist.
     */
    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<List<BookDTO>> booksByAuthor(@PathVariable Long authorId) throws EntityNotFoundException {
        List<BookDTO> books = authorService.findAuthorByID(authorId)
                .orElseThrow(() -> new EntityNotFoundException(Author.class))
                .getBooks()
                .stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    /**
     * Creates an order for the specified customer with a list of books and quantities
     * and returns the created order as an OrdersDTO.
     *
     * @param customerName The name of the customer.
     * @param orders       A map of books and quantities.
     * @return A ResponseEntity with the created order in DTO format.
     * @throws InsufficientUnitsException If insufficient units of any book are available.
     * @throws EntityNotFoundException If a book is not found.
     * @throws EntityAlreadyExistsException If the order already exists.
     */
    @PostMapping("/orders")
    public ResponseEntity<OrdersDTO> makeOrder(
            @RequestParam String customerName,
            @RequestBody HashMap<Book, Integer> orders)
            throws InsufficientUnitsException, EntityNotFoundException, EntityAlreadyExistsException {

        // Check availability of all books in the order
        for (Book bookFromList : orders.keySet()) {
            Book bookFromRepo = bookService.findBookById(bookFromList.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Book.class));

            if (!bookFromRepo.getTitle().equals(bookFromList.getTitle())) {
                throw new EntityNotFoundException(Book.class);
            }

            int requestedQuantity = orders.get(bookFromList);
            if (bookFromRepo.getStockUnits() < requestedQuantity) {
                throw new InsufficientUnitsException();
            }
        }

        // Create the order
        OrdersDTO createdOrderDTO = new OrdersDTO(orderService.createOrder(customerName, orders));
        return ResponseEntity.ok(createdOrderDTO);
    }
}
