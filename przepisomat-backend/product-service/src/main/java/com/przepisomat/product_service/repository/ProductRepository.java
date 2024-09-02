package com.przepisomat.product_service.repository;

import com.przepisomat.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getProductByUsername(String username);

    Product getProductById(Long id);

    Optional<Product> findProductByNameAndUsername(String name, String username);
}
