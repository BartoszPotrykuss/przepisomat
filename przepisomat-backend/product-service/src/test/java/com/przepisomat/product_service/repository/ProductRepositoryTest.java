package com.przepisomat.product_service.repository;

import com.przepisomat.product_service.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void ProductRepository_findProductByNameAndUsername_returnProduct() {
        //Arrange
        String name = "ryż";
        String username = "user";

        Product newProduct = Product.builder()
                .name(name)
                .amount(500L)
                .username(username)
                .build();

        //Act
        productRepository.save(newProduct);

        //Assert
        Optional<Product> optionalProduct = productRepository.findProductByNameAndUsername(name, username);
        assertThat(optionalProduct.get()).isEqualTo(newProduct);
    }

    @Test
    public void ProductRepository_getProductById_returnProduct() {
        //Arrange
        Product newProduct = Product.builder()
                .name("ryż")
                .amount(500L)
                .username("user")
                .build();

        //Act
        productRepository.save(newProduct);

        //Assert
        Product product = productRepository.getProductById(newProduct.getId());
        assertThat(product).isEqualTo(newProduct);
    }

    @Test
    public void ProductRepository_getProductByUsername_returnProduct() {
        //Arrange
        String username = "user";

        Product newProduct = Product.builder()
                .name("ryż")
                .amount(500L)
                .username(username)
                .build();

        Product newProduct2 = Product.builder()
                .name("ryż")
                .amount(500L)
                .username("user2")
                .build();

        //Act
        productRepository.save(newProduct);
        productRepository.save(newProduct2);

        //Assert
        List<Product> productList = productRepository.getProductByUsername(username);
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(1);
    }
}
