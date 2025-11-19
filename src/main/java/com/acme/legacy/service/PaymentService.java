package com.acme.legacy.service;

import java.util.*;
import java.time.LocalDateTime;

public class PaymentService {
    
    private Map<Long, Payment> payments = new HashMap<>();
    private Map<Long, List<Transaction>> transactionHistory = new HashMap<>();
    
    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, PAYPAL, BANK_TRANSFER, CRYPTO
    }
    
    public enum PaymentStatus {
        PENDING, PROCESSING, COMPLETED, FAILED, REFUNDED
    }
    
    public Payment createPayment(Long orderId, Long userId, double amount, PaymentMethod method) {
        Payment payment = new Payment();
        payment.setId(System.currentTimeMillis());
        payment.setOrderId(orderId);
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        
        payments.put(payment.getId(), payment);
        logTransaction(payment.getId(), "Payment created", PaymentStatus.PENDING);
        
        return payment;
    }
    
    public Payment processPayment(Long paymentId) {
        Payment payment = payments.get(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found");
        }
        
        payment.setStatus(PaymentStatus.PROCESSING);
        logTransaction(paymentId, "Payment processing started", PaymentStatus.PROCESSING);
        
        // Simulate payment processing
        boolean success = simulatePaymentGateway(payment);
        
        if (success) {
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setCompletedAt(LocalDateTime.now());
            logTransaction(paymentId, "Payment completed successfully", PaymentStatus.COMPLETED);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Payment gateway declined");
            logTransaction(paymentId, "Payment failed", PaymentStatus.FAILED);
        }
        
        return payment;
    }
    
    public Payment refundPayment(Long paymentId, String reason) {
        Payment payment = payments.get(paymentId);
        if (payment == null || payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot refund this payment");
        }
        
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundReason(reason);
        payment.setRefundedAt(LocalDateTime.now());
        
        logTransaction(paymentId, "Payment refunded: " + reason, PaymentStatus.REFUNDED);
        
        return payment;
    }
    
    private boolean simulatePaymentGateway(Payment payment) {
        return Math.random() > 0.1;
    }
    
    private void logTransaction(Long paymentId, String description, PaymentStatus status) {
        Transaction tx = new Transaction();
        tx.setTimestamp(LocalDateTime.now());
        tx.setDescription(description);
        tx.setStatus(status);
        
        transactionHistory.computeIfAbsent(paymentId, k -> new ArrayList<>()).add(tx);
    }
    
    public Payment getPayment(Long paymentId) {
        return payments.get(paymentId);
    }
    
    public List<Payment> getPaymentsByUser(Long userId) {
        List<Payment> userPayments = new ArrayList<>();
        for (Payment payment : payments.values()) {
            if (payment.getUserId().equals(userId)) {
                userPayments.add(payment);
            }
        }
        return userPayments;
    }
    
    static class Payment {
        private Long id;
        private Long orderId;
        private Long userId;
        private double amount;
        private PaymentMethod method;
        private PaymentStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime completedAt;
        private LocalDateTime refundedAt;
        private String failureReason;
        private String refundReason;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        public PaymentMethod getMethod() { return method; }
        public void setMethod(PaymentMethod method) { this.method = method; }
        public PaymentStatus getStatus() { return status; }
        public void setStatus(PaymentStatus status) { this.status = status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public LocalDateTime getCompletedAt() { return completedAt; }
        public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
        public String getFailureReason() { return failureReason; }
        public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    }
    
    static class Transaction {
        private LocalDateTime timestamp;
        private String description;
        private PaymentStatus status;
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public PaymentStatus getStatus() { return status; }
        public void setStatus(PaymentStatus status) { this.status = status; }
    }
}