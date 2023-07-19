package com.example.Facturation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.Facturation.domain.Category;
import com.example.Facturation.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setName("Bebidas");
        category.setDescription("Toda clase de bebida");
    }

    @DisplayName("Save Category")
    @Test
    void save() {
        //given
        given(categoryRepository.save(category)).willReturn(category);

        //when
        Category categorySaved = categoryService.save(category);

        //then
        assertThat(categorySaved).isNotNull();
    }

    @DisplayName("Find All Categories")
    @Test
    void findAll() {
        //given
        Category category1 = new Category();
        category1.setName("demo");
        category1.setDescription("Toda clase de bebida");
        given(categoryRepository.findAll()).willReturn(List.of(category1,category));

        //when
        List<Category> categories = categoryService.findAll();

        //then
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(2);
    }
}