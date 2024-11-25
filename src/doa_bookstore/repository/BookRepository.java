package doa_bookstore.repository;

import doa_bookstore.entity.Book;

public class BookRepository extends MyCrudRepository<Book> {

    private static BookRepository instance;
    private BookRepository(){};

    @SuppressWarnings("unchecked")
    public static synchronized BookRepository getInstance() {
        if (instance == null) {
            try {
                instance = new BookRepository();
            } catch (Exception e) {
                throw new RuntimeException("Unable to create singleton instance for "
                        + BookRepository.class.getSimpleName(), e);
            }
        }
        return instance;
    }

}