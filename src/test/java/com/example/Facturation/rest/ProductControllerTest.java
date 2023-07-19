package com.example.Facturation.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Facturation.domain.Category;
import com.example.Facturation.domain.Product;
import com.example.Facturation.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProducts() throws Exception {
        //given
        Category category = new Category();
        category.setCategory_id(1);

        Product product = new Product();
        product.setProduct_id(1);
        product.setName("Product");
        product.setPrice(3.00f);
        product.setStock(2);
        product.setCategory(category);

        Product product2 = new Product();
        product2.setProduct_id(2);
        product2.setName("Product2");
        product2.setPrice(3.00f);
        product2.setStock(2);
        product2.setCategory(category);

        List<Product> products = Arrays.asList(product, product2);
        given(productService.findAll()).willReturn(products);

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/products"));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(products.size())));
    }

    @Test
    void create() throws Exception {
        //given
        Category category = new Category();
        category.setCategory_id(1);

        Product product = new Product();
        product.setProduct_id(1);
        product.setName("Product");
        product.setPrice(3.00f);
        product.setStock(2);
        product.setCategory(category);

        given(productService.save(any(Product.class))).willReturn(product);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").exists())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.stock").value(product.getStock()));

    }
}