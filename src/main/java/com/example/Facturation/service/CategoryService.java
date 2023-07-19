package com.example.Facturation.service;

import com.example.Facturation.domain.Category;
import java.util.List;

public interface CategoryService {

    Category save(Category category);
    List<Category> findAll();

}
