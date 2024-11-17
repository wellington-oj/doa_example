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

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @BeforeAll
    static void startContainer() {
        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @Test
    void testSaveBook() throws EntityAlreadyExistsException, EntityNotFoundException {
        Author author = new Author("Jane Austen");
        authorService.saveAuthor(author);
        Book book = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 10);

        BookDTO savedBookDTO = bookstoreController.saveBook(book).getBody();
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

        OrdersDTO createdOrderDTO = bookstoreController.makeOrder("Alice", orders).getBody();
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

        assertThrows(InsufficientUnitsException.class, () -> bookstoreController.makeOrder("Alice", orders));
    }

    @Test
    void testMakeOrderBookNotFound() {
        HashMap<Book, Integer> orders = new HashMap<>();
        Book book = new Book("Nonexistent Book", null, Book.Genre.DRAMA, 5);
        book.setId(1L);
        orders.put(book, 2);

        assertThrows(EntityNotFoundException.class, () -> bookstoreController.makeOrder("Alice", orders));
    }
}
