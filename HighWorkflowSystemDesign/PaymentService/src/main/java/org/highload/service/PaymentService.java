package org.highload.service;

package com.highload.service;

import com.highload.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment processPayment(Payment payment);
    Optional<Payment> getPaymentByTransactionId(String transactionId);
    List<Payment> getPaymentsByOrderId(Long orderId);
    List<Payment> getPaymentsByStatus(Payment.PaymentStatus status);
    List<Payment> getUserPaymentsInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    BigDecimal getTotalUserPayments(Long userId);
    List<Payment> getLargePayments(BigDecimal amount, Payment.PaymentStatus status);
}