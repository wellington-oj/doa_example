package doa_bookstore.repository;

import doa_bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>{
    Optional<Book> findByTitle(String title);
}