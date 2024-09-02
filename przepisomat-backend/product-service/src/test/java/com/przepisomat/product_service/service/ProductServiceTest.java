package com.przepisomat.product_service.service;

import com.przepisomat.product_service.dto.ProductDto;
import com.przepisomat.product_service.entity.Product;
import com.przepisomat.product_service.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ProductService productService;

    @InjectMocks
    private JwtService jwtService;

    @Test
    public void addProduct_whenProductDoesNotExist_returnsSavedProduct() {
        // Arrange
        String username = "testUser";
        String token = jwtService.generateToken("testUser");


        ProductDto productDto = new ProductDto("Product1", 10L);
        Product newProduct = Product.builder()
                .name(productDto.getName())
                .amount(productDto.getAmount())
                .username(username)
                .build();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(productRepository.findProductByNameAndUsername(productDto.getName(), username))
                .thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        // Act
        Product result = productService.addProduct(productDto, request);

        // Assert
        assertEquals(newProduct, result);
    }

    @Test
    public void addProduct_whenProductExist_updatesAndReturnsProduct() {
        //Arrange
        String username = "testUser";
        String token = jwtService.generateToken("testUser");

        ProductDto productDto = new ProductDto("Product1", 10L);
        Product existingProduct = Product.builder()
                .name(productDto.getName())
                .amount(5L)
                .username(username)
                .build();

        Product updatedProduct = Product.builder()
                .name(productDto.getName())
                .amount(existingProduct.getAmount() + productDto.getAmount())
                .username(username)
                .build();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(productRepository.findProductByNameAndUsername(productDto.getName(), username))
                .thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        //Act
        Product result = productService.addProduct(productDto, request);

        //Assert
        assertEquals(updatedProduct, result);
    }

    @Test
    public void updateProductAmount_whenGivenAmount_updatesAndReturnsProduct() {
        //Arrange
        String username = "testUser";
        String token = jwtService.generateToken("testUser");

        ProductDto productDto = new ProductDto("Product1", 10L);
        Product newProduct = Product.builder()
                .id(1L)
                .name(productDto.getName())
                .amount(productDto.getAmount())
                .username(username)
                .build();

        when(productRepository.getProductById(anyLong())).thenReturn(newProduct);
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        //Act
        productService.updateProductAmount(newProduct.getId(), 20L);

        //Assert
        assertEquals(newProduct.getAmount(), 20L);
    }
}
