package doa_bookstore.entity;

import doa_bookstore.entity.interfaces.Entity;

/**
 * Represents a book in the bookstore system.
 * Contains information about the book's ID, title, author, genre, and stock units.
 */
public class Book implements Entity<Long> {

    /**
     * Enum representing the genre of the book.
     */
    public enum Genre {
        HORROR, ROMANCE, DRAMA, COMEDY, SCIFI
    }

    private Long id;
    private String title;
    private Author author;
    private Genre genre;
    private int stockUnits;

    /**
     * Default constructor for creating an empty {@code Book}.
     */
    public Book() {}

    /**
     * Constructs a new {@code Book} with the specified title, author, genre, and stock units.
     *
     * @param title      The title of the book.
     * @param author     The author of the book.
     * @param genre      The genre of the book.
     * @param stockUnits The number of units in stock for the book.
     */
    public Book(String title, Author author, Genre genre, int stockUnits) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.stockUnits = stockUnits;
    }

    /**
     * Gets the ID of the book.
     *
     * @return The ID of the book.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the book.
     *
     * @param id The ID to set for the book.
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title The new title to set for the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     *
     * @return The author of the book.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author The new author to set for the book.
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Gets the genre of the book.
     *
     * @return The genre of the book.
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the book.
     *
     * @param genre The new genre to set for the book.
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Gets the number of stock units available for the book.
     *
     * @return The number of stock units.
     */
    public int getStockUnits() {
        return stockUnits;
    }

    /**
     * Sets the number of stock units available for the book.
     *
     * @param stockUnits The new number of stock units.
     */
    public void setStockUnits(int stockUnits) {
        this.stockUnits = stockUnits;
    }
}
