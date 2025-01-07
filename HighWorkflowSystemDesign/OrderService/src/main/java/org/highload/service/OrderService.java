package org.highload.service;

package com.highload.service;

import com.highload.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrdersByStatus(Order.OrderStatus status);
    List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> getOrdersByUserIdAndStatus(Long userId, Order.OrderStatus status);
    long countOrdersByUserIdAndStatus(Long userId, Order.OrderStatus status);
}