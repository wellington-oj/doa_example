package doa_bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Library {

    @Id
    private Long id;

    @ManyToMany
    private Set<Book> books;

}


