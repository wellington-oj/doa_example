package test.doa_bookstore.service;

import doa_bookstore.entity.Author;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.repository.AuthorRepository;
import doa_bookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    private AuthorRepository authorRepository;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorRepository = AuthorRepository.getInstance();
        authorService = new AuthorService(authorRepository);
    }

    @Test
    void testSaveAuthor() {
        // Test saving a new author
        Author author = new Author("John Doe");
        assertDoesNotThrow(() -> {
            Author savedAuthor = authorService.saveAuthor(author);
            assertNotNull(savedAuthor.getId());
            assertEquals("John Doe", savedAuthor.getName());
        });

        // Test attempting to save the same author again (should throw an exception)
        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            authorService.saveAuthor(author);
        });

        assertEquals("Author already exists.", exception.getMessage());
    }

    @Test
    void testFindAuthorById() throws EntityAlreadyExistsException {
        Author author = new Author("John Doe");
        authorService.saveAuthor(author);

        assertDoesNotThrow(() -> {
            Author foundAuthor = authorService.findAuthorByID(author.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Author.class));
            assertEquals("John Doe", foundAuthor.getName());
        });

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            authorService.findAuthorByID(-1L).orElseThrow(() ->
                    new EntityNotFoundException(Author.class));});

        assertEquals("Author not found.", exception.getMessage());
    }

}
