package doa_bookstore.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an author in the bookstore system.
 * Contains information about the author's ID, name, and a list of books written by the author.
 */
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    /**
     * Default constructor for creating an empty {@code Author}.
     */
    public Author() {}

    /**
     * Constructs a new {@code Author} with the specified name.
     *
     * @param name The name of the author.
     */
    public Author(String name) {
        this.name = name;
    }

    /**
     * Sets the ID of the author.
     *
     * @param id The ID to set for the author.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the author.
     *
     * @return The ID of the author.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the name of the author.
     *
     * @return The name of the author.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     *
     * @param name The new name to set for the author.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of books written by the author.
     *
     * @return A list of books written by the author.
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of books written by the author.
     *
     * @param books The new list of books to associate with the author.
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Adds a book to the list of books.
     *
     * @param book The new book to associate with the author.
     */
    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this); // Ensure the relationship is set on both sides
    }
}
