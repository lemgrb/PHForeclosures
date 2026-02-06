package com.phforeclosures.repository;

import com.phforeclosures.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Custom query methods can be added here if needed
    // For example:
    // List<Property> findByPriceGreaterThan(BigDecimal price);
    // List<Property> findByAddressContaining(String address);
}