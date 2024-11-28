package doa_bookstore.controller;

import doa_bookstore.dto.AuthorDTO;
import doa_bookstore.dto.BookDTO;
import doa_bookstore.dto.OrdersDTO;
import doa_bookstore.entity.Author;
import doa_bookstore.entity.Book;
import doa_bookstore.entity.Orders;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.exception.InsufficientUnitsException;
import doa_bookstore.service.AuthorService;
import doa_bookstore.service.BookService;
import doa_bookstore.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookstoreControllerTest {

    @Autowired
    private BookstoreController bookstoreController;
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private OrderService orderService;

    @Test
    void testFindAllBooks() throws EntityAlreadyExistsException, EntityNotFoundException {
        Author author1 = new Author("Jane Austen");
        authorService.saveAuthor(author1);

        Author author2 = new Author("Charles Dickens");
        authorService.saveAuthor(author2);

        Book book1 = new Book("Pride and Prejudice", author1, Book.Genre.ROMANCE, 10);
        Book book2 = new Book("Great Expectations", author2, Book.Genre.DRAMA, 8);

        bookService.saveBook(book1);
        bookService.saveBook(book2);

        ResponseEntity<List<BookDTO>> response = bookstoreController.getAllBooks();

        List<BookDTO> books = response.getBody();
        assertNotNull(books);
        assertEquals(3, books.size()); //there is a test book in the database
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Pride and Prejudice")));
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Great Expectations")));
    }

    @Test
    void testSaveBook() throws EntityAlreadyExistsException, EntityNotFoundException {
        Author author = new Author("Jane Austen");
        authorService.saveAuthor(author);
        Book book = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 10);

        BookDTO savedBookDTO = bookstoreController.saveBook(new BookDTO(book)).getBody();
        assertNotNull(savedBookDTO);
        assertNotNull(savedBookDTO.getId());
        assertEquals("Pride and Prejudice", savedBookDTO.getTitle());

        author.addBook(bookService.findBookById(savedBookDTO.getId()).orElseThrow());
        authorService.updateAuthor(author);

        Author repoAuthor = authorService.findAuthorByID(author.getId())
                .orElseThrow(() -> new EntityNotFoundException(Author.class));

        assertTrue(repoAuthor.getBooks().stream()
                .anyMatch(b -> b.getId().equals(savedBookDTO.getId())));
    }

    @Test
    void testFindAuthorById() throws EntityAlreadyExistsException, EntityNotFoundException {
        Author author = new Author("Jane Austen");
        authorService.saveAuthor(author);

        AuthorDTO foundAuthorDTO = bookstoreController.findAuthorById(author.getId()).getBody();
        assertNotNull(foundAuthorDTO);
        assertEquals("Jane Austen", foundAuthorDTO.getName());
    }

    @Test
    void testBooksByAuthor() throws EntityAlreadyExistsException, EntityNotFoundException {
        Author author = new Author("Jane Austen");
        authorService.saveAuthor(author);

        Book book1 = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 10);
        Book book2 = new Book("Emma", author, Book.Genre.ROMANCE, 5);
        bookService.saveBook(book1);
        bookService.saveBook(book2);

        author.addBook(book1);
        author.addBook(book2);
        authorService.updateAuthor(author);

        List<BookDTO> books = bookstoreController.booksByAuthor(author.getId()).getBody();
        assertNotNull(books);
        assertEquals(2, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Pride and Prejudice")));
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Emma")));
    }

    @Transactional
    @Test
    void testMakeOrder() throws EntityAlreadyExistsException, InsufficientUnitsException, EntityNotFoundException {
        Author author = new Author("Jane Austen");
        authorService.saveAuthor(author);

        Book book = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 10);
        bookService.saveBook(book);

        author.addBook(book);
        authorService.updateAuthor(author);

        HashMap<Book, Integer> orders = new HashMap<>();
        orders.put(book, 2);

        Orders order = new Orders("Alice",orders, LocalDateTime.now(), Orders.OrderStatus.PENDING);

        OrdersDTO createdOrderDTO = bookstoreController.makeOrder(new OrdersDTO(order)).getBody();
        assertNotNull(createdOrderDTO);
        assertEquals("Alice", createdOrderDTO.getCustomerName());
        assertEquals(Orders.OrderStatus.PENDING, createdOrderDTO.getStatus());
        assertTrue(createdOrderDTO.getBooks().containsKey(new BookDTO(book)));
    }

    @Test
    void testMakeOrderInsufficientUnits() throws EntityAlreadyExistsException, EntityNotFoundException {
        Author author = new Author("Jane Austen");
        authorService.saveAuthor(author);

        Book book = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 1);
        bookService.saveBook(book);
        author.addBook(book);
        authorService.updateAuthor(author);

        HashMap<Book, Integer> orders = new HashMap<>();
        orders.put(book, 2);

        Orders order = new Orders("Alice",orders, LocalDateTime.now(), Orders.OrderStatus.PENDING);

        assertThrows(InsufficientUnitsException.class, () -> bookstoreController.makeOrder(new OrdersDTO(order)));
    }

    @Test
    void testMakeOrderBookNotFound() {
        HashMap<Book, Integer> orders = new HashMap<>();
        Author author = new Author();
        author.setId(1L);
        Book book = new Book("Nonexistent Book", author, Book.Genre.DRAMA, 5);
        book.setId(100L);
        orders.put(book, 2);
        Orders order = new Orders("Alice",orders, LocalDateTime.now(), Orders.OrderStatus.PENDING);

        assertThrows(EntityNotFoundException.class, () -> bookstoreController.makeOrder(new OrdersDTO(order)));
    }
}
