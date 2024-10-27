package test.doa_bookstore.controller;

import doa_bookstore.controller.BookstoreController;
import doa_bookstore.entity.Author;
import doa_bookstore.entity.Book;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.exception.InsufficientUnitsException;
import doa_bookstore.repository.AuthorRepository;
import doa_bookstore.repository.BookRepository;
import doa_bookstore.repository.OrderRepository;
import doa_bookstore.service.AuthorService;
import doa_bookstore.service.BookService;
import doa_bookstore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookstoreControllerTest {

    private BookstoreController bookstoreController;
    private BookService bookService;
    private AuthorService authorService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        BookRepository bookRepository = BookRepository.getInstance();
        AuthorRepository authorRepository = AuthorRepository.getInstance();
        OrderRepository orderRepository = OrderRepository.getInstance();

        bookService = new BookService(bookRepository);
        authorService = new AuthorService(authorRepository);
        orderService = new OrderService(orderRepository);

        bookstoreController = new BookstoreController(bookService, authorService, orderService);
    }

    @Test
    void testSaveBook() throws EntityAlreadyExistsException, EntityNotFoundException {
        Author author = new Author("Jane Austen");
        authorService.saveAuthor(author);
        Book book = new Book("Pride and Prejudice", author, Book.Genre.ROMANCE, 10);

        Book savedBook = bookstoreController.saveBook(book);

        assertNotNull(savedBook.getId());
        assertEquals("Pride and Prejudice", savedBook.getTitle());

        author.addBook(savedBook);
        authorService.updateAuthor(author);

        Author repoAuthor = authorService.findAuthorByID(author.getId())
                .orElseThrow(() -> new EntityNotFoundException(Author.class));

        assertTrue(repoAuthor.getBooks().contains(savedBook));
    }


    @Test
    void testFindAuthorById() throws EntityAlreadyExistsException, EntityNotFoundException {
        Author author = new Author("Jane Austen");
        authorService.saveAuthor(author);

        Author foundAuthor = bookstoreController.findAuthorById(author.getId());
        assertNotNull(foundAuthor);
        assertEquals("Jane Austen", foundAuthor.getName());
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

        Author updatedAuthor = authorService.updateAuthor(author);

        List<Book> books = bookstoreController.booksByAuthor(updatedAuthor);
        assertEquals(2, books.size());
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

        boolean isOrderSuccessful = bookstoreController.makeOrder("Alice", orders);
        assertTrue(isOrderSuccessful);
    }

    @Test
    void testMakeOrderInsufficientUnits() throws EntityAlreadyExistsException {
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
