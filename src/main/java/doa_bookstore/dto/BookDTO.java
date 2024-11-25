package doa_bookstore.dto;

import doa_bookstore.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) for the {@link Book} entity.
 * This class is used to transfer book data between different layers of the application.
 * It provides a simplified representation of the Book entity, focusing only on essential fields.
 *
 */
@Schema(description = "DTO representing a Book")
public class BookDTO {

    private Long id;
    private String title;
    private Long authorId;
    private String genre;
    private Integer stockQuantity;

    /**
     * Default no-argument constructor for BookDTO.
     */
    public BookDTO() {}

    /**
     * Constructs a {@code BookDTO} from a {@link Book} entity.
     * Copies the ID, title, and author's name from the provided Book to the DTO.
     *
     * @param book The Book entity from which to create the DTO.
     */
    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorId = book.getAuthor().getId();
        this.genre = book.getGenre().toString();
        this.stockQuantity = book.getStockUnits();
    }

    /**
     * Constructs a {@code BookDTO} with specific ID, title, and author id.
     *
     * @param id         The ID of the book.
     * @param title      The title of the book.
     * @param authorName The id of the author of the book.
     */
    public BookDTO(Long id, String title, Long authorName) {
        this.id = id;
        this.title = title;
        this.authorId = authorName;
    }

    /**
     * Gets the ID of the book.
     *
     * @return The ID of the book.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the book.
     *
     * @param id The new ID to set for the book.
     */
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
     * Gets the id of the author of the book.
     *
     * @return The id of the author.
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * Sets the id of the author of the book.
     *
     * @param authorId The new name to set for the author.
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * Returns a string representation of the {@code BookDTO}.
     * This representation includes the ID, title, and author's name of the book.
     *
     * @return A string representation of the {@code BookDTO}.
     */
    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(id, bookDTO.id) && Objects.equals(title, bookDTO.title) && Objects.equals(authorId, bookDTO.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorId);
    }
}
