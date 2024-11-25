package doa_bookstore.dto;

import doa_bookstore.entity.Author;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for the {@link Author} entity.
 * This class is used to transfer author data between different layers of the application.
 * It provides a simplified representation of the Author entity, focusing only on essential fields.
 *
 */
@Schema(description = "DTO representing an Author")
public class AuthorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;

    /**
     * Constructs an {@code AuthorDTO} from an {@link Author} entity.
     * Copies the ID and name from the provided Author to the DTO.
     *
     * @param author The Author entity from which to create the DTO.
     */
    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }
    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDTO authorDTO = (AuthorDTO) o;
        return Objects.equals(id, authorDTO.id) && Objects.equals(name, authorDTO.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    /**
     * Default no-argument constructor for AuthorDTO.
     */
    public AuthorDTO() {}



    /**
     * Constructs an {@code AuthorDTO} with specific ID and name.
     *
     * @param id The ID of the author.
     * @param name The name of the author.
     */
    public AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
     * Sets the ID of the author.
     *
     * @param id The new ID to set for the author.
     */
    public void setId(Long id) {
        this.id = id;
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
     * Returns a string representation of the {@code AuthorDTO}.
     * This representation includes the ID and name of the author.
     *
     * @return A string representation of the {@code AuthorDTO}.
     */

}
