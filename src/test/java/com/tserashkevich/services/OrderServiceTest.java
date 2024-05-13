package com.tserashkevich.services;

import com.tserashkevich.models.Order;
import com.tserashkevich.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserService userService;
    @Mock
    private ReservationService reservationService;
    @Mock
    private ItemService itemService;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, userService, reservationService, itemService);
    }

    @Test
    void findAll_ShouldReturnListOfOrders() {
        // Arrange
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<Order> result = orderService.findAll();

        // Assert
        assertEquals(orders, result);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void save_ShouldSaveOrder() {
        // Arrange
        Order order = new Order();

        // Act
        orderService.save(order);

        // Assert
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void delete_ShouldDeleteOrder() {
        // Arrange
        Long orderId = 1L;

        // Act
        orderService.delete(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
