package doa_bookstore.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Author entity.
 */
class AuthorTest {

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("John Doe");
    }

    @Test
    void testGetId() {
        author.setId(1L);
        assertEquals(1L, author.getId());
    }

    @Test
    void testGetName() {
        assertEquals("John Doe", author.getName());
    }

    @Test
    void testSetName() {
        author.setName("Jane Doe");
        assertEquals("Jane Doe", author.getName());
    }

    @Test
    void testGetBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Book 1", author, Book.Genre.DRAMA, 5));
        author.setBooks(books);
        assertEquals(books, author.getBooks());
    }

    @Test
    void testAddBook() {
        author.addBook(new Book("Book 1", author, Book.Genre.DRAMA, 5));
        assertEquals(author.getBooks().get(0).getTitle(),"Book 1");
    }
}
