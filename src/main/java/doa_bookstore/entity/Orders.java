package doa_bookstore.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an order in the bookstore system.
 * Contains information about the order ID, the customer who placed the order,
 * the map of books with quantities, the date of the order, and the status of the order.
 */
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    @ElementCollection
    @CollectionTable(name = "order_books", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "book_id")
    @Column(name = "quantity")
    private Map<Book, Integer> books = new HashMap<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
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
     * Default constructor required by JPA.
     */
    public Orders() {}

    /**
     * Constructs a new {@code Orders} with the specified parameters.
     *
     * @param customerName The name of the customer who placed the order.
     * @param books        The map of books in the order and their quantities.
     * @param orderDate    The date the order was placed.
     * @param status       The status of the order.
     */
    public Orders(String customerName, Map<Book, Integer> books, Date orderDate, OrderStatus status) {
        this.customerName = customerName;
        this.books = books;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(Map<Book, Integer> books) {
        this.books = books;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

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
