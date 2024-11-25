package doa_bookstore.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Order entity.
 */
class OrderTest {

    private Orders order;
    private Author author;
    private Book book;
    private HashMap<Book, Integer> books;


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
    @BeforeEach
    void setUp() {
        author = new Author("George Orwell");
        book = new Book("1984", author, Book.Genre.SCIFI, 20);
        books = new HashMap<>();
        books.put(book, 3);
        order = new Orders("Alice Johnson", books, LocalDateTime.now(), Orders.OrderStatus.PENDING);
    }

    @Test
    void testGetId() {
        order.setId(1L);
        assertEquals(1L, order.getId());
    }

    @Test
    void testGetCustomerName() {
        assertEquals("Alice Johnson", order.getCustomerName());
    }

    @Test
    void testSetCustomerName() {
        order.setCustomerName("Bob Smith");
        assertEquals("Bob Smith", order.getCustomerName());
    }

    @Test
    void testGetBooks() {
        assertEquals(books, order.getBooks());
    }

    @Test
    void testSetBooks() {
        HashMap<Book, Integer> newBooks = new HashMap<>();
        newBooks.put(new Book("Animal Farm", author, Book.Genre.SCIFI, 15), 2);
        order.setBooks(newBooks);
        assertEquals(newBooks, order.getBooks());
    }

    @Test
    void testSetOrderDate() {
        order.setOrderDate(LocalDateTime.now());
        assertEquals(LocalDateTime.now(), order.getOrderDate());
    }

    @Test
    void testGetStatus() {
        assertEquals(Orders.OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void testSetStatus() {
        order.setStatus(Orders.OrderStatus.COMPLETED);
        assertEquals(Orders.OrderStatus.COMPLETED, order.getStatus());
    }
}
