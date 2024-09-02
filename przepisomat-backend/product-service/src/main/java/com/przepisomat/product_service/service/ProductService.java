package com.przepisomat.product_service.service;

import com.przepisomat.product_service.dto.ProductDto;
import com.przepisomat.product_service.entity.Product;
import com.przepisomat.product_service.repository.ProductRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(ProductDto productDto, HttpServletRequest request) {


        String authorizationHeader = request.getHeader("Authorization");
        String username = getSubjectFromJwtToken(authorizationHeader);

        Product product = Product.builder()
                .name(productDto.getName())
                .amount(productDto.getAmount())
                .username(username)
                .build();

        Optional<Product> sameProduct = productRepository.findProductByNameAndUsername(product.getName(), product.getUsername());

        if (sameProduct.isPresent()) {
            Product updatedProduct = sameProduct.get();
            updatedProduct.setAmount(sameProduct.get().getAmount() + product.getAmount());
            return productRepository.save(updatedProduct);
        }

        return productRepository.save(product);
    }


    public List<Product> getProductsByUsername(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        String username = getSubjectFromJwtToken(authorizationHeader);
        return productRepository.getProductByUsername(username);
    }

    public void updateProductAmount(Long id, Long amount) {
        Product product = productRepository.getProductById(id);
        product.setAmount(amount);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private String getSubjectFromJwtToken(String authorizationHeader) {

        // Wyciągnij sam token JWT z nagłówka
        String jwtToken = authorizationHeader.substring(7);

        // Odczytaj token i pobierz z niego dane
        Claims claims = Jwts.parser().
                setSigningKey("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437")
                .parseClaimsJws(jwtToken)
                .getBody();

        // Pobierz identyfikator użytkownika z tokena JWT
        return claims.getSubject();
    }
}
