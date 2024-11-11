package doa_bookstore.service;

import doa_bookstore.entity.Book;
import doa_bookstore.entity.Orders;
import doa_bookstore.entity.Orders.OrderStatus;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.exception.EntityNotFoundException;
import doa_bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * Service class for managing orders in the bookstore system.
 * Provides business logic for creating, updating, and retrieving orders.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    /**
     * Creates a new order with the given customer name and list of books.
     * The order status is set to PENDING, and the order date is set to the current date.
     *
     * @param customerName The name of the customer placing the order.
     * @param books        The list of books included in the order.
     * @return The created Order.
     * @throws EntityAlreadyExistsException if an order with the same details already exists.
     */
    public Orders createOrder(String customerName, HashMap<Book, Integer> books) throws EntityAlreadyExistsException {
        Orders order = new Orders(customerName, books, new Date(), OrderStatus.PENDING);

        // Optional: Prevent duplicates by checking for existing orders with the same customer and date
        Optional<Orders> existingOrder = orderRepository.findByCustomerNameAndOrderDate(customerName, order.getOrderDate());
        if (existingOrder.isPresent()) {
            throw new EntityAlreadyExistsException(Orders.class);
        }

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
     * Updates the status of an existing order.
     *
     * @param orderId The ID of the order to update.
     * @param status  The new status for the order.
     * @throws EntityNotFoundException if the order with the specified ID does not exist.
     */
    public Orders updateOrderStatus(Long orderId, OrderStatus status) throws EntityNotFoundException {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(Orders.class));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     */
    public void deleteOrder(Long orderId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException(Orders.class);
        }
        orderRepository.deleteById(orderId);
    }
}
