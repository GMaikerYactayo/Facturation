package com.example.Facturation.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Facturation.domain.Category;
import com.example.Facturation.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }


    @Test
    void testGetAllCategories() throws Exception {
        //given
        Category category = new Category();
        category.setName("Bebidas");
        category.setDescription("Toda clase de bebida");

        Category category2 = new Category();
        category2.setName("enlatado");
        category2.setDescription("Toda clase de enlatado");

        List<Category> categories = Arrays.asList(category, category2);
        given(categoryService.findAll()).willReturn(categories);

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/category"));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(categories.size()));
    }

    @Test
    void testCreateCategory() throws Exception {
        //given
        Category category = new Category();
        category.setName("Bebidas");
        category.setDescription("Toda clase de bebida");
        given(categoryService.save(any(Category.class))).willReturn(category);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(category)));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category_id").exists())
                .andExpect(jsonPath("$.name").value(category.getName()))
                .andExpect(jsonPath("$.description").value(category.getDescription()));
    }

}