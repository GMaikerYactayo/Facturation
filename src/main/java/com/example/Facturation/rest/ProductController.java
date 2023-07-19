package com.example.Facturation.rest;

import com.example.Facturation.domain.Product;
import com.example.Facturation.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

}
