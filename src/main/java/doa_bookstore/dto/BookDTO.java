package doa_bookstore.dto;

import doa_bookstore.entity.Book;

/**
 *
 * THIS CLASS WILL NOT BE USED ON THE FIRST PROJECT.
 * ITS HERE BECAUSE YOU SHOULD BE AWARE HOW THIS WORKS
 * IT WILL BE ASKED FOR YOU TO WORK WITH DTOS IN THE THIRD PROJECT
 *
 *
 * Data Transfer Object (DTO) for the {@link Book} entity.
 * This class is used to transfer book data between different layers of the application.
 * It provides a simplified representation of the Book entity, focusing only on essential fields.
 */
public class BookDTO {

    private Long id;
    private String title;
    private String authorName;

    /**
     * Constructs a {@code BookDTO} from a {@link Book} entity.
     * Copies the ID, title, and author's name from the provided Book to the DTO.
     *
     * @param book The Book entity from which to create the DTO.
     */
    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorName = book.getAuthor().getName();
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
     * Gets the name of the author of the book.
     *
     * @return The name of the author.
     */
    public String getAuthorName() {
        return authorName;
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
                ", author='" + authorName + '\'' +
                '}';
    }
}
