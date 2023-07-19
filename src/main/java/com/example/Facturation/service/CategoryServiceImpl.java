package com.example.Facturation.service;

import com.example.Facturation.domain.Category;
import com.example.Facturation.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public Category save(Category category) {
        log.info("Creating / Updating category");
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        log.info("Executing findAll category");
        return categoryRepository.findAll();
    }
}
