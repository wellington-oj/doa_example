package doa_bookstore.startup;

import doa_bookstore.repository.AuthorRepository;
import doa_bookstore.repository.BookRepository;
import doa_bookstore.repository.OrderRepository;
import doa_bookstore.service.AuthorService;
import doa_bookstore.service.BookService;
import doa_bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

public class StartupInitializer {

    @Autowired
    private final AuthorService authorService;
    @Autowired
    private final BookService bookService;
    @Autowired
    private final OrderService orderService;

    public StartupInitializer() {


        this.authorService = new AuthorService();
        this.bookService = new BookService();
        this.orderService = new OrderService();
    }
}
