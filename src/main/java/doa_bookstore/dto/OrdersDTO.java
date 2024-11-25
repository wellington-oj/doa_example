package doa_bookstore.dto;

import doa_bookstore.entity.Book;
import doa_bookstore.entity.Orders;
import doa_bookstore.entity.Orders.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for the {@link Orders} entity.
 * This class is used to transfer order data between different layers of the application.
 * It provides a simplified representation of the Orders entity, focusing on essential fields.
 */
@Schema(description = "DTO representing a Orders")
public class OrdersDTO {

    private Long id;
    private String customerName;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private Map<BookDTO, Integer> books;

    /**
     * Default no-argument constructor for OrdersDTO.
     */
    public OrdersDTO() {}

    /**
     * Constructs an {@code OrdersDTO} from an {@link Orders} entity.
     * Copies the ID, customer name, status, order date, and books with quantities from the provided Orders entity.
     *
     * @param order The Orders entity from which to create the DTO.
     */
    public OrdersDTO(Orders order) {
        this.id = order.getId();
        this.customerName = order.getCustomerName();
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();

        this.books = new HashMap<>();
        order.getBooks().forEach((book, quantity) -> this.books.put(new BookDTO(book), quantity));
    }

    /**
     * Gets the ID of the order.
     *
     * @return The ID of the order.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id The new ID for the order.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the customer's name for the order.
     *
     * @return The customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer's name for the order.
     *
     * @param customerName The customer's name.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the status of the order.
     *
     * @return The status of the order.
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status The new status for the order.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Gets the order date.
     *
     * @return The order date.
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the order date.
     *
     * @param orderDate The new order date.
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the map of books and their quantities in the order.
     *
     * @return A map of books and their quantities.
     */
    public Map<BookDTO, Integer> getBooks() {
        return books;
    }

    /**
     * Sets the map of books and their quantities in the order.
     *
     * @param books The new map of books and quantities.
     */
    public void setBooks(Map<BookDTO, Integer> books) {
        this.books = books;
    }

    /**
     * Returns a string representation of the {@code OrdersDTO}.
     *
     * @return A string representation of the {@code OrdersDTO}.
     */
    @Override
    public String toString() {
        return "OrdersDTO{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersDTO ordersDTO = (OrdersDTO) o;
        return Objects.equals(id, ordersDTO.id) && Objects.equals(customerName, ordersDTO.customerName) && status == ordersDTO.status && Objects.equals(orderDate, ordersDTO.orderDate) && Objects.equals(books, ordersDTO.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, status, orderDate, books);
    }
}
