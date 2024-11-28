package doa_bookstore.repository;

import doa_bookstore.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    /**
     * Finds an order by customer name and order date.
     * Used to check for duplicate orders for the same customer on the same date.
     *
     * @param customerName The name of the customer who placed the order.
     * @param orderDate    The date the order was placed.
     * @return An Optional containing the found order, or an empty Optional if no order is found.
     */
    Optional<Orders> findByCustomerNameAndOrderDate(String customerName, LocalDateTime orderDate);
}
