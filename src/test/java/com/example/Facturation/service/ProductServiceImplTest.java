package com.example.Facturation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.Facturation.domain.Category;
import com.example.Facturation.domain.Product;
import com.example.Facturation.repository.CategoryRepository;
import com.example.Facturation.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;

    private Category category;

    @InjectMocks
    private ProductServiceImpl productService;


    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCategory_id(1);
        category.setName("Bebidas");
        category.setDescription("Toda clase de bebida");
        categoryRepository.save(category);
    }

    @DisplayName("Save product")
    @Test
    void save() {
        //given
        given(categoryRepository.findById(category.getCategory_id()))
                .willReturn(Optional.of(category));
        Product product = new Product();
        product.setName("Product");
        product.setPrice(2.00f);
        product.setStock(20);
        product.setCategory(category);
        given(productRepository.save(product)).willReturn(product);

        //when
        Product productSaved = productService.save(product);

        //then
        assertThat(productSaved).isNotNull();
    }

    @Test
    void findAll() {
        //given
        Product product = new Product();
        product.setProduct_id(1);
        product.setName("Product");
        product.setPrice(2.00f);
        product.setStock(20);
        product.setCategory(category);

        Product product2 = new Product();
        product.setProduct_id(2);
        product2.setName("Agua");
        product2.setPrice(1.00f);
        product.setStock(20);
        product2.setCategory(category);

        given(productRepository.findAll()).willReturn(List.of(product, product2));

        //when
        List<Product> products = productService.findAll();

        //then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(2);
    }

}