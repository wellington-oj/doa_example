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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookstore")
@Tag(name = "Bookstore API", description = "API for managing books, authors, and orders in the bookstore.")
public class BookstoreController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the bookstore.")
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks().stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get all authors", description = "Retrieve a list of all authors in the bookstore.")
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors().stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authors);
    }

    @Operation(summary = "Save a new book", description = "Add a new book to the bookstore.")
    @PostMapping("/books")
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO)
            throws EntityAlreadyExistsException, EntityNotFoundException {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(authorService.findAuthorByID(bookDTO.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(Author.class)));

        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(new BookDTO(savedBook));
    }

    @Operation(summary = "Get author by ID", description = "Retrieve an author by their ID.")
    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable Long id)
            throws EntityNotFoundException {
        Author author = authorService.findAuthorByID(id)
                .orElseThrow(() -> new EntityNotFoundException(Author.class));
        return ResponseEntity.ok(new AuthorDTO(author));
    }

    @Operation(summary = "Save a new author", description = "Add a new author to the bookstore.")
    @PostMapping("/authors")
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody AuthorDTO authorDTO)
            throws EntityAlreadyExistsException {
        Author author = new Author();
        author.setName(authorDTO.getName());

        Author savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(new AuthorDTO(savedAuthor));
    }

    @Operation(summary = "Get books by author", description = "Retrieve all books written by a specific author.")
    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<List<BookDTO>> booksByAuthor(@PathVariable Long authorId)
            throws EntityNotFoundException {
        List<BookDTO> books = authorService.findAuthorByID(authorId)
                .orElseThrow(() -> new EntityNotFoundException(Author.class))
                .getBooks()
                .stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Create an order", description = "Place a new order for books.")
    @PostMapping("/orders")
    public ResponseEntity<OrdersDTO> makeOrder(@RequestBody OrdersDTO orderDTO)
            throws InsufficientUnitsException, EntityNotFoundException, EntityAlreadyExistsException {
        String customerName = orderDTO.getCustomerName();
        Map<BookDTO, Integer> bookOrders = orderDTO.getBooks();

        HashMap<Book, Integer> orders = new HashMap<>();

        for (BookDTO bookDTO : bookOrders.keySet()) {
            Book book = bookService.findBookById(bookDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Book.class));
            if(!bookDTO.getTitle().equals(book.getTitle()))
                throw new EntityNotFoundException(Book.class);
            int requestedQuantity = bookOrders.get(bookDTO);
            if (book.getStockUnits() < requestedQuantity) {
                throw new InsufficientUnitsException();
            }
            orders.put(book, requestedQuantity);
        }

        OrdersDTO createdOrderDTO = new OrdersDTO(orderService.createOrder(customerName, orders));
        return ResponseEntity.ok(createdOrderDTO);
    }
}
