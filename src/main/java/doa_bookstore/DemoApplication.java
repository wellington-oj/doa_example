package doa_bookstore;

import doa_bookstore.entity.Author;
import doa_bookstore.entity.Book;
import doa_bookstore.entity.Book.Genre;
import doa_bookstore.repository.AuthorRepository;
import doa_bookstore.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            authorRepository.deleteAll();
            bookRepository.deleteAll();
            Author machado = new Author("Machado de Assis");
            Author markTwain = new Author("Mark Twain");
            authorRepository.save(machado);
            authorRepository.save(markTwain);

            Book book = new Book("Test", machado, Genre.DRAMA, 100);
            bookRepository.save(book);
        };
    }
}

