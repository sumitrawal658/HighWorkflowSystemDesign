package org.highload.service.impl;

package com.highload.service.impl;

import com.highload.model.Payment;
import com.highload.repository.PaymentRepository;
import com.highload.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment processPayment(Payment payment) {
        if (payment == null) {
            throw new ValidationException("Payment object cannot be null.");
        }
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Payment amount must be greater than zero.");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentByTransactionId(String transactionId) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new ValidationException("Transaction ID cannot be null or empty.");
        }
        return paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for transaction ID: " + transactionId));
    }

    @Override
    public List<Payment> getPaymentsByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new ValidationException("Order ID must be a positive number.");
        }
        return paymentRepository.findByOrderId(orderId);
    }

    @Override
    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        if (status == null) {
            throw new ValidationException("Payment status cannot be null.");
        }
        return paymentRepository.findByStatus(status);
    }

    @Override
    public List<Payment> getUserPaymentsInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        if (startDate == null || endDate == null) {
            throw new ValidationException("Start date and end date cannot be null.");
        }
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date must be before end date.");
        }
        return paymentRepository.findUserPaymentsInDateRange(userId, startDate, endDate);
    }

    @Override
    public BigDecimal getTotalUserPayments(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        return paymentRepository.getTotalUserPayments(userId);
    }

    @Override
    public List<Payment> getLargePayments(BigDecimal amount, Payment.PaymentStatus status) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Amount must be greater than zero.");
        }
        if (status == null) {
            throw new ValidationException("Payment status cannot be null.");
        }
        return paymentRepository.findLargePayments(amount, status);
    }
}