package com.example.Facturation.service;

import com.example.Facturation.domain.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);
    List<Product> findAll();

}
