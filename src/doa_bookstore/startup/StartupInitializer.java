package doa_bookstore.startup;

import doa_bookstore.repository.AuthorRepository;
import doa_bookstore.repository.BookRepository;
import doa_bookstore.repository.OrderRepository;
import doa_bookstore.service.AuthorService;
import doa_bookstore.service.BookService;
import doa_bookstore.service.OrderService;

public class StartupInitializer {

    private final AuthorService authorService;
    private final BookService bookService;
    private final OrderService orderService;

    public StartupInitializer() {
        BookRepository bookRepository = BookRepository.getInstance();
        AuthorRepository authorRepository = AuthorRepository.getInstance();
        OrderRepository orderRepository = OrderRepository.getInstance();

        this.authorService = new AuthorService(authorRepository);
        this.bookService = new BookService(bookRepository);
        this.orderService = new OrderService(orderRepository);
    }
}
