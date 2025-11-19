package com.acme.legacy.controller;

import com.acme.legacy.service.UserService;
import com.acme.legacy.service.OrderService;
import com.acme.legacy.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ApiController {
    
    private UserService userService = new UserService();
    private OrderService orderService = new OrderService();
    private ProductService productService = new ProductService();
    
    @GetMapping("/users")
    public List<UserService.User> getUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping("/users")
    public UserService.User createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(
            request.getName(),
            request.getEmail(),
            request.getPhone(),
            request.getAddress()
        );
    }
    
    @GetMapping("/orders")
    public List<OrderService.Order> getOrders(@RequestParam Long userId) {
        return orderService.getOrdersByUser(userId);
    }
    
    @GetMapping("/products")
    public List<ProductService.Product> getProducts() {
        return productService.getAllProducts();
    }
    
    static class CreateUserRequest {
        private String name;
        private String email;
        private String phone;
        private String address;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }
}