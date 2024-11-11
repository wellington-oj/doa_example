package doa_bookstore.entity;

import doa_bookstore.entity.interfaces.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an author in the bookstore system.
 * Contains information about the author's ID, name, and a list of books written by the author.
 */
public class Author implements Entity<Long> {

    private Long id;
    private String name;
    private List<Book> books;

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
        this.books = new ArrayList<>();
    }

    /**
     * Sets the ID of the author.
     *
     * @param id The ID to set for the author.
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the author.
     *
     * @return The ID of the author.
     */
    @Override
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
     * Add a book to the list of books
     *
     * @param book The new book to associate with the author.
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

}
