package doa_bookstore.service;

import doa_bookstore.entity.Book;
import doa_bookstore.entity.Orders;
import doa_bookstore.entity.Orders.OrderStatus;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.repository.MyCrudRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * Service class for managing orders in the bookstore system.
 * Provides business logic for creating, updating, and retrieving orders.
 */
public class OrderService {

    private final MyCrudRepository<Orders> orderRepository;

    /**
     * Constructs a new {@code OrderService} with the specified order repository.
     *
     * @param orderRepository The repository for managing Order entities.
     */
    public OrderService(MyCrudRepository<Orders> orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new order with the given customer name and list of books.
     * The order status is set to PENDING, and the order date is set to the current date.
     *
     * @param customerName The name of the customer placing the order.
     * @param books        The list of books included in the order.
     * @return The created Order.
     */
    public Orders createOrder(String customerName, HashMap<Book, Integer> books) throws EntityAlreadyExistsException {
        Orders order = new Orders(customerName, books, new Date(), OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    /**
     * Finds an order by its ID.
     *
     * @param orderId The ID of the order to find.
     * @return An Optional containing the found Order, or an empty Optional if not found.
     */
    public Optional<Orders> findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     */
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
