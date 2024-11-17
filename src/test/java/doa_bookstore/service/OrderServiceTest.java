package doa_bookstore.service;

import doa_bookstore.entity.Book;
import doa_bookstore.entity.Orders;
import doa_bookstore.exception.EntityAlreadyExistsException;
import doa_bookstore.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @BeforeAll
    static void startContainer() {
        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @BeforeEach
    void setUp() {
        // Optional setup logic for each test, if needed.
    }

    @Test
    void testCreateOrder() {
        // Prepare the books for the order
        HashMap<Book, Integer> books = new HashMap<>();
        books.put(new Book("Book 1", null, Book.Genre.DRAMA, 5), 2);

        // Test creating a new order
        assertDoesNotThrow(() -> {
            Orders order = orderService.createOrder("Alice Johnson", books);
            assertNotNull(order.getId(), "Order should have an ID after being saved.");
            assertEquals("Alice Johnson", order.getCustomerName(), "Order's customer name should match the provided name.");
            assertEquals(Orders.OrderStatus.PENDING, order.getStatus(), "New order status should be PENDING.");
        });
    }

    @Test
    void testFindOrderById() throws EntityAlreadyExistsException {
        // Prepare the books for the order
        HashMap<Book, Integer> books = new HashMap<>();
        books.put(new Book("Book 1", null, Book.Genre.DRAMA, 5), 2);

        // Save a new order
        Orders order = orderService.createOrder("Alice Johnson", books);

        // Test finding the existing order
        assertDoesNotThrow(() -> {
            Optional<Orders> foundOrder = orderService.findOrderById(order.getId());
            assertTrue(foundOrder.isPresent(), "Order should be found.");
            assertEquals("Alice Johnson", foundOrder.get().getCustomerName(), "Found order's customer name should match.");
            assertEquals(Orders.OrderStatus.PENDING, foundOrder.get().getStatus(), "Found order's status should be PENDING.");
        });

        // Test finding a non-existing order (should return an empty Optional)
        Optional<Orders> nonExistentOrder = orderService.findOrderById(-1L);
        assertFalse(nonExistentOrder.isPresent(), "Non-existent order should not be found.");
    }

    @Test
    void testDeleteOrder() throws EntityAlreadyExistsException {
        // Prepare the books for the order
        HashMap<Book, Integer> books = new HashMap<>();
        books.put(new Book("Book 1", null, Book.Genre.DRAMA, 5), 2);

        // Save a new order
        Orders order = orderService.createOrder("Alice Johnson", books);
        Long orderId = order.getId();

        // Ensure the order exists before deletion
        assertTrue(orderService.findOrderById(orderId).isPresent(), "Order should exist before deletion.");

        // Delete the order
        assertDoesNotThrow(() -> {
            orderService.deleteOrder(orderId);
        }, "Deleting an existing order should not throw an exception.");

        // Verify the order no longer exists
        Optional<Orders> deletedOrder = orderService.findOrderById(orderId);
        assertFalse(deletedOrder.isPresent(), "Order should no longer exist after deletion.");
    }
}
