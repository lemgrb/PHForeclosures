package com.phforeclosures.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.phforeclosures.model.PropertyRequest;
import com.phforeclosures.model.Property;
import com.phforeclosures.repository.PropertyRepository;

@RestController
@RequestMapping("/api")
public class HealthController {

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("/health")
    public String health() {
        return "UP";
    }

    /**
     * Saves a new property to the database.
     * Accepts property details including title, description, price, address and image URL list.
     *
     * @param request the property request containing all property details
     * @return ResponseEntity containing the saved property
     */
    @PostMapping("/properties")
    public ResponseEntity<Property> saveProperty(@RequestBody PropertyRequest request) {
        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setPrice(request.getPrice());
        property.setAddress(request.getAddress());
        property.setImageUrls(request.getImageUrls());
        
        Property savedProperty = propertyRepository.save(property);
        return ResponseEntity.ok(savedProperty);
    }
}
