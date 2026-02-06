package com.phforeclosures.model;

import java.math.BigDecimal;
import java.util.List;

public class PropertyRequest {
    
    private String title;
    private String description;
    private BigDecimal price;
    private String address;
    private List<String> imageUrls;
    
    // Default constructor
    public PropertyRequest() {}
    
    // Constructor with parameters
    public PropertyRequest(String title, String description, BigDecimal price, String address, List<String> imageUrls) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.address = address;
        this.imageUrls = imageUrls;
    }
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<String> getImageUrls() {
        return imageUrls;
    }
    
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}