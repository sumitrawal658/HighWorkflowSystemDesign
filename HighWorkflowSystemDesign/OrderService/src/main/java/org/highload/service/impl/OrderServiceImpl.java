
package com.highload.service.impl;

import com.highload.model.Order;
import com.highload.repository.OrderRepository;
import com.highload.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final InventoryService inventoryService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public Order createOrder(Order order) {
        // Validate input
        if (order == null) {
            throw new ValidationException("Order cannot be null.");
        }
        if (order.getUserId() == null || order.getUserId() <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }

        // Check if user exists
        if (!userRepository.existsById(order.getUserId())) {
            throw new ResourceNotFoundException("User with ID " + order.getUserId() + " does not exist.");
        }

        // Check inventory stock
        boolean isStockAvailable = inventoryService.checkStockAvailability(order.getProductId(), order.getQuantity());
        if (!isStockAvailable) {
            throw new ValidationException("Product is out of stock.");
        }

        // Place order
        Order savedOrder = orderRepository.save(order);

        // Update inventory stock
        inventoryService.updateStock(order.getProductId(), order.getQuantity());

        return savedOrder;
    }
}

    @Override
    public Order createOrder(Order order) {
        if (order == null) {
            throw new ValidationException("Order object cannot be null.");
        }
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for user ID: " + userId);
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        if (status == null) {
            throw new ValidationException("Order status cannot be null.");
        }
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new ValidationException("Start date and end date cannot be null.");
        }
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date must be before end date.");
        }
        return orderRepository.findOrdersInDateRange(startDate, endDate);
    }

    @Override
    public List<Order> getOrdersByUserIdAndStatus(Long userId, Order.OrderStatus status) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        if (status == null) {
            throw new ValidationException("Order status cannot be null.");
        }
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public long countOrdersByUserIdAndStatus(Long userId, Order.OrderStatus status) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        if (status == null) {
            throw new ValidationException("Order status cannot be null.");
        }
        return orderRepository.countByUserIdAndStatus(userId, status);
    }
}