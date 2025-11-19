package com.acme.legacy.service;

import java.util.*;
import java.time.LocalDateTime;

public class UserService {
    private Map<Long, User> users = new HashMap<>();
    
    public User createUser(String name, String email, String phone, String address) {
        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus("ACTIVE");
        users.put(user.getId(), user);
        return user;
    }
    
    public User getUser(Long id) {
        return users.get(id);
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public User updateUser(Long id, String name, String email) {
        User user = users.get(id);
        if (user != null) {
            if (name != null) user.setName(name);
            if (email != null) user.setEmail(email);
            user.setUpdatedAt(LocalDateTime.now());
        }
        return user;
    }
    
    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }
    
    public List<User> searchUsers(String query) {
        List<User> results = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getName().contains(query) || user.getEmail().contains(query)) {
                results.add(user);
            }
        }
        return results;
    }
    
    public long getUserCount() {
        return users.size();
    }
    
    public List<User> getActiveUsers() {
        List<User> active = new ArrayList<>();
        for (User user : users.values()) {
            if ("ACTIVE".equals(user.getStatus())) {
                active.add(user);
            }
        }
        return active;
    }
    
    static class User {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
}