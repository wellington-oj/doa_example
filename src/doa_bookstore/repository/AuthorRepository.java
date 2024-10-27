package doa_bookstore.repository;

import doa_bookstore.entity.Author;

public class AuthorRepository extends MyCrudRepository<Author> {
    private static AuthorRepository instance;

    public static synchronized AuthorRepository getInstance() {
        if (instance == null) {
            try {
               instance = new AuthorRepository();
            } catch (Exception e) {
                throw new RuntimeException("...");
            }
        }
        return instance;
    }
}


