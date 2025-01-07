package service.impl;

import org.highload.userservice.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class OrderServiceImpl {

    @Test
    void testGetOrdersByUserId_InvalidUserId_ThrowsValidationException() {
        Assertions.assertThrows(GlobalExceptionHandler.ValidationException.class, () -> orderService.getOrdersByUserId(-1L));
    }

    @Test
    void testGetOrdersByUserId_NoOrdersFound_ThrowsResourceNotFoundException() {
        when(orderRepository.findByUserId(1L)).thenReturn(Collections.emptyList());
        Assertions.assertThrows(GlobalExceptionHandler.ResourceNotFoundException.class, () -> orderService.getOrdersByUserId(1L));
    }
}
