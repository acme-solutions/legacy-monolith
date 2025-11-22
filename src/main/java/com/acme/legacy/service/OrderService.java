package com.acme.legacy.service;

import java.util.*;
import java.time.LocalDateTime;

public class OrderService {
    private Map<Long, Order> orders = new HashMap<>();
    private Map<Long, List<OrderItem>> orderItems = new HashMap<>();
    
    public Order createOrder(Long userId, List<OrderItem> items) {
        Order order = new Order();
        order.setId(System.currentTimeMillis());
        order.setUserId(userId);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotal(total);
        
        orders.put(order.getId(), order);
        orderItems.put(order.getId(), items);
        return order;
    }
    
    public Order getOrder(Long id) {
        return orders.get(id);
    }
    
    public List<Order> getOrdersByUser(Long userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getUserId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }
    
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
        }
        return order;
    }
    
    public boolean cancelOrder(Long orderId) {
        Order order = orders.get(orderId);
        if (order != null && "PENDING".equals(order.getStatus())) {
            order.setStatus("CANCELLED");
            return true;
        }
        return false;
    }
    
    public double calculateOrderTotal(Long orderId) {
        List<OrderItem> items = orderItems.get(orderId);
        if (items == null) return 0;
        
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
    
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItems.get(orderId);
    }
    
    static class Order {
        private Long id;
        private Long userId;
        private String status;
        private Double total;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public Double getTotal() { return total; }
        public void setTotal(Double total) { this.total = total; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
    
    static class OrderItem {
        private Long productId;
        private String productName;
        private int quantity;
        private double price;
        
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}