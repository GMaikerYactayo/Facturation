package com.example.Facturation.service;

import com.example.Facturation.domain.Category;
import com.example.Facturation.domain.Product;
import com.example.Facturation.repository.CategoryRepository;
import com.example.Facturation.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product save(Product product) {
        Category category = categoryRepository.findById(product.getCategory().getCategory_id())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        product.setCategory(category);

        log.info("Creating / Updating product");
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        log.info("Executing findAll product");
        return productRepository.findAll();
    }

}
