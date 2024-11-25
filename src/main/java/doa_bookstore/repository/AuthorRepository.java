package doa_bookstore.repository;

import doa_bookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String customerName);

    // Query to retrieve an author and the count of their books
    @Query("SELECT a, (SELECT COUNT(b) FROM Book b WHERE b.author = a) AS bookCount " +
            "FROM Author a WHERE a.name = :name")
    Optional<Object[]> findAuthorAndBookCountByName(@Param("name") String name);

    @Query("SELECT a FROM Author a WHERE a.birthDate > :birthDate")
    List<Author> findAuthorsBornAfter(@Param("birthDate") LocalDate birthDate);

    @Query("SELECT a FROM Author a WHERE a.address.city = :city")
    List<Author> findAuthorsByCity(@Param("city") String city);

    @Query("SELECT a FROM Author a WHERE SIZE(a.books) > :bookCount")
    List<Author> findAuthorsWithMoreThanBooks(@Param("bookCount") int bookCount);

    @Query("SELECT a FROM Author a WHERE a.books IS EMPTY")
    List<Author> findAuthorsWithNoBooks();

    @Query("SELECT AVG(SIZE(a.books)) FROM Author a")
    Double findAverageNumberOfBooksPerAuthor();


}


