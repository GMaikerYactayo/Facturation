package com.example.Facturation.rest;

import com.example.Facturation.domain.Category;
import com.example.Facturation.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("/category")
    public List<Category> getAllCategories() {
        log.info("REST request to find all category");
        return categoryService.findAll();
    }

    @PostMapping("/category")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        log.info("REST request to create a new category");
        return ResponseEntity.ok(this.categoryService.save(category));
    }

}
