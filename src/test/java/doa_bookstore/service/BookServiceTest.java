package doa_bookstore.service;

import doa_bookstore.entity.Author;
import doa_bookstore.entity.Book;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.repository.AuthorRepository;
import doa_bookstore.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        // Optional: Initialize anything needed for each test.
    }

    @Test
    void testSaveBook() {
        // Test saving a new book
        Author author = new Author("Jane Austen");
        authorRepository.save(author);
        Book book = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 10);

        assertDoesNotThrow(() -> {
            Book savedBook = bookService.saveBook(book);
            assertNotNull(savedBook.getId(), "Saved book should have an ID.");
            assertEquals("Pride and Prejudice", savedBook.getTitle(), "Saved book's title should match the provided title.");
            assertEquals(author, savedBook.getAuthor(), "Saved book's author should match the provided author.");
        });

        // Test attempting to save the same book again (should throw an exception)
        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            bookService.saveBook(book);
        }, "Saving the same book again should throw an EntityAlreadyExistsException.");

        assertEquals("Book already exists.", exception.getMessage());
    }

    @Test
    void testFindBookById() throws EntityAlreadyExistsException {
        // Test finding an existing book
        Author author = new Author("Jane Austen");
        authorRepository.save(author);
        Book book = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 10);
        bookService.saveBook(book);

        assertDoesNotThrow(() -> {
            Optional<Book> foundBook = bookService.findBookById(book.getId());
            assertTrue(foundBook.isPresent(), "Book should be found.");
            assertEquals("Pride and Prejudice", foundBook.get().getTitle(), "Found book's title should match the saved book's title.");
        });

        // Test attempting to find a non-existing book (should return an empty Optional)
        Optional<Book> nonExistentBook = bookService.findBookById(-1L);
        assertFalse(nonExistentBook.isPresent(), "Non-existent book should not be found.");
    }
}
