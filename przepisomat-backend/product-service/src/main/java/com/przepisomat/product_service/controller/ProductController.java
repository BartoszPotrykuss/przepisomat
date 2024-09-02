package com.przepisomat.product_service.controller;

import com.przepisomat.product_service.dto.ProductDto;
import com.przepisomat.product_service.dto.UpdateProductRequest;
import com.przepisomat.product_service.entity.Product;
import com.przepisomat.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {
        Product product = productService.addProduct(productDto, request);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProductsByUsername(HttpServletRequest request) {
        List<Product> products = productService.getProductsByUsername(request);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProductAmount(@PathVariable Long id ,@RequestBody UpdateProductRequest request) {
        productService.updateProductAmount(id, request.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
