package doa_bookstore.startup;

import doa_bookstore.service.AuthorService;
import doa_bookstore.service.BookService;
import doa_bookstore.service.OrderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartupInitializer {

    private final AuthorService authorService;
    private final BookService bookService;
    private final OrderService orderService;

    @Autowired
    public StartupInitializer(AuthorService authorService, BookService bookService, OrderService orderService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @PostConstruct
    public void init() {
        // Add any startup logic here, such as populating initial data
        System.out.println("StartupInitializer: Application has started, and services are initialized.");
    }
}
