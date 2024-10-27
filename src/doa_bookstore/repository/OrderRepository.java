package doa_bookstore.repository;

import doa_bookstore.entity.Orders;

public class OrderRepository extends MyCrudRepository<Orders>{

    private static OrderRepository instance;

    @SuppressWarnings("unchecked")
    public static synchronized OrderRepository getInstance() {
        if (instance == null) {
            try {
                instance = new OrderRepository();
            } catch (Exception e) {
                throw new RuntimeException("Unable to create singleton instance for "
                        + OrderRepository.class.getSimpleName(), e);
            }
        }
        return instance;
    }

}
