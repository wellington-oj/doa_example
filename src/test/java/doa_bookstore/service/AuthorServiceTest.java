package doa_bookstore.service;

import doa_bookstore.entity.Author;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    void testSaveAuthor() {
        Author author = new Author("John Doe");
        assertDoesNotThrow(() -> {
            Author savedAuthor = authorService.saveAuthor(author);
            assertNotNull(savedAuthor.getId());
            assertEquals("John Doe", savedAuthor.getName());
        });

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            authorService.saveAuthor(author);
        });

        assertEquals("Author already exists.", exception.getMessage());
    }


    @Test
    void testFindAuthorById() throws EntityAlreadyExistsException {
        // Save an author to test retrieval
        Author author = new Author("John Doe");
        authorService.saveAuthor(author);

        // Find the author by ID and check the name
        assertDoesNotThrow(() -> {
            Author foundAuthor = authorService.findAuthorByID(author.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Author.class));
            assertEquals("John Doe", foundAuthor.getName());
        });

        // Attempt to find a non-existent author, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            authorService.findAuthorByID(-1L).orElseThrow(() ->
                    new EntityNotFoundException(Author.class));
        });

        assertEquals("Author not found.", exception.getMessage());
    }
}
