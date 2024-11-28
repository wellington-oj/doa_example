package doa_bookstore.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Book entity.
 */
class BookTest {

    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Jane Austen");
        book = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 10);
    }

    @Test
    void testGetId() {
        book.setId(1L);
        assertEquals(1L, book.getId());
    }

    @Test
    void testGetTitle() {
        assertEquals("Pride and Prejudice", book.getTitle());
    }

    @Test
    void testSetTitle() {
        book.setTitle("Emma");
        assertEquals("Emma", book.getTitle());
    }

    @Test
    void testGetAuthor() {
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testSetAuthor() {
        Author newAuthor = new Author("Mark Twain");
        book.setAuthor(newAuthor);
        assertEquals(newAuthor, book.getAuthor());
    }

    @Test
    void testGetGenre() {
        assertEquals(Book.Genre.ROMANCE, book.getGenre());
    }

    @Test
    void testSetGenre() {
        book.setGenre(Book.Genre.DRAMA);
        assertEquals(Book.Genre.DRAMA, book.getGenre());
    }

    @Test
    void testGetStockUnits() {
        assertEquals(10, book.getStockUnits());
    }

    @Test
    void testSetStockUnits() {
        book.setStockUnits(15);
        assertEquals(15, book.getStockUnits());
    }
}
