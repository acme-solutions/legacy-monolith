package com.acme.legacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
@RestController
public class LegacyApplication {

    private static Map<Long, User> userDatabase = new HashMap<>();
    private static Map<Long, Order> orderDatabase = new HashMap<>();
    private static Map<Long, Product> productDatabase = new HashMap<>();
    
    public static void main(String[] args) {
        SpringApplication.run(LegacyApplication.class, args);
    }
    
    @GetMapping("/api/users")
    public List<User> getUsers() {
        return new ArrayList<>(userDatabase.values());
    }
    
    @GetMapping("/api/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userDatabase.get(id);
    }
    
    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {
        userDatabase.put(user.getId(), user);
        return user;
    }
    
    @GetMapping("/api/orders")
    public List<Order> getOrders() {
        return new ArrayList<>(orderDatabase.values());
    }
    
    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return new ArrayList<>(productDatabase.values());
    }
    
    static class User {
        private Long id;
        private String name;
        private String email;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
    
    static class Order {
        private Long id;
        private Long userId;
        private Double total;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public Double getTotal() { return total; }
        public void setTotal(Double total) { this.total = total; }
    }
    
    static class Product {
        private Long id;
        private String name;
        private Double price;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }
}