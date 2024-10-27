package doa_bookstore.entity;

import doa_bookstore.entity.interfaces.Entity;

import java.util.Date;
import java.util.HashMap;

/**
 * Represents an order in the bookstore system.
 * Contains information about the order ID, the customer who placed the order,
 * the map of books with quantities, the date of the order, and the status of the order.
 */
public class Orders implements Entity<Long> {

    private Long id;
    private String customerName;
    private HashMap<Book, Integer> books;
    private Date orderDate;
    private OrderStatus status;

    /**
     * Enum representing the possible status of an order.
     */
    public enum OrderStatus {
        PENDING,
        COMPLETED,
        CANCELLED
    }

    /**
     * Constructs a new {@code Order} with the specified parameters.
     *
     * @param customerName The name of the customer who placed the order.
     * @param books        The map of books in the order and their quantities.
     * @param orderDate    The date the order was placed.
     * @param status       The status of the order.
     */
    public Orders(String customerName, HashMap<Book, Integer> books, Date orderDate, OrderStatus status) {
        this.customerName = customerName;
        this.books = books;
        this.orderDate = orderDate;
        this.status = status;
    }

    /**
     * Gets the ID of the order.
     *
     * @return The ID of the order.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id The new ID for the order.
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the customer who placed the order.
     *
     * @return The customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer who placed the order.
     *
     * @param customerName The new customer name.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the map of books in the order with their quantities.
     *
     * @return The map of books and quantities.
     */
    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    /**
     * Sets the map of books in the order with their quantities.
     *
     * @param books The new map of books and quantities.
     */
    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    /**
     * Gets the date the order was placed.
     *
     * @return The order date.
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date the order was placed.
     *
     * @param orderDate The new order date.
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the status of the order.
     *
     * @return The order status.
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status The new order status.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the {@code Order}.
     * This representation includes the order ID, customer name, order date, and status.
     *
     * @return A string representation of the {@code Order}.
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", orderDate=" + orderDate +
                ", status=" + status +
                '}';
    }
}
