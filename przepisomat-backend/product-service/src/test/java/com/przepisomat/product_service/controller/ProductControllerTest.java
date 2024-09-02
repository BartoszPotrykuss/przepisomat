package com.przepisomat.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.przepisomat.product_service.dto.ProductDto;
import com.przepisomat.product_service.dto.UpdateProductRequest;
import com.przepisomat.product_service.entity.Product;
import com.przepisomat.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addProduct_shouldReturnCreatedProduct() throws Exception {
        //Arrange
        ProductDto productDto = new ProductDto("ryż", 200L);
        Product product = Product.builder()
                .name("ryż")
                .amount(200L)
                .build();

        Mockito.when(productService.addProduct(any(ProductDto.class), any(HttpServletRequest.class))).thenReturn(product);

        //Act and Assert
        mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("ryż"))
                .andExpect(jsonPath("$.amount").value(200L));
    }

    @Test
    public void getProductsByUsername_shouldReturnListOfProducts() throws Exception {
        // Arrange
        List<Product> products = Arrays.asList(
                Product.builder().name("ryż").amount(200L).build(),
                Product.builder().name("makaron").amount(150L).build()
        );

        Mockito.when(productService.getProductsByUsername(any(HttpServletRequest.class)))
                .thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ryż"))
                .andExpect(jsonPath("$[0].amount").value(200L))
                .andExpect(jsonPath("$[1].name").value("makaron"))
                .andExpect(jsonPath("$[1].amount").value(150L));
    }

    @Test
    public void updateProductAmount_shouldReturnOk() throws Exception {
        // Arrange
        UpdateProductRequest updateRequest = new UpdateProductRequest(300L);

        Mockito.doNothing().when(productService).updateProductAmount(anyLong(), anyLong());

        // Act & Assert
        mockMvc.perform(put("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());
    }

}
