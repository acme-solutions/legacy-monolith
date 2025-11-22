package com.acme.legacy.service;

import java.util.*;

public class ProductService {
    private Map<Long, Product> products = new HashMap<>();
    private Map<String, List<Product>> categoryIndex = new HashMap<>();
    
    public Product createProduct(String name, String description, double price, String category) {
        Product product = new Product();
        product.setId(System.currentTimeMillis());
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setInStock(true);
        product.setStock(100);
        
        products.put(product.getId(), product);
        categoryIndex.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
        return product;
    }
    
    public Product getProduct(Long id) {
        return products.get(id);
    }
    
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
    
    public List<Product> getProductsByCategory(String category) {
        return categoryIndex.getOrDefault(category, new ArrayList<>());
    }
    
    public Product updateStock(Long productId, int quantity) {
        Product product = products.get(productId);
        if (product != null) {
            product.setStock(quantity);
            product.setInStock(quantity > 0);
        }
        return product;
    }
    
    public List<Product> searchProducts(String query) {
        List<Product> results = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getName().contains(query) || product.getDescription().contains(query)) {
                results.add(product);
            }
        }
        return results;
    }
    
    static class Product {
        private Long id;
        private String name;
        private String description;
        private double price;
        private String category;
        private int stock;
        private boolean inStock;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }
        public boolean isInStock() { return inStock; }
        public void setInStock(boolean inStock) { this.inStock = inStock; }
    }
}