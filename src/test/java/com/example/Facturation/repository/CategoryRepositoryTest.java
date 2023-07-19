package com.example.Facturation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.Facturation.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setName("Bebidas");
        category.setDescription("Toda clase de bebida");
    }


    @DisplayName("Test save category")
    @Test
    void testSaveCategory() {
        //given - dado o condición previa o configuración

        //when - acción o el comportamiento que vamos a probar
        Category category1Save = categoryRepository.save(category);

        //then - verificar la salida
        assertThat(category1Save).isNotNull();
        assertThat(category1Save.getCategory_id()).isGreaterThan(0);
    }

    @DisplayName("Test find all category")
    @Test
    void testFindAllCategory() {
        //given - dado el condición previa o configuración
        Category category1 = new Category();
        category1.setName("demo");
        category1.setDescription("Toda clase de bebida");
        categoryRepository.save(category);

        //when - acción o el comportamiento que vamos a probar
        List<Category> categoryList = categoryRepository.findAll();

        //then - verificar la salida
        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(2);
    }

    @DisplayName("Test find category by id")
    @Test
    void testFindCategoryById() {
        //given
        categoryRepository.save(category);

        //when - acción o el comportamiento que vamos a probar
        Category categoryBD = categoryRepository.findById(category.getCategory_id()).get();

        //then - verificar la salida
        assertThat(categoryBD).isNotNull();
    }

    @DisplayName("Test update category")
    @Test
    void testUpdateCategory() {
        //given
        categoryRepository.save(category);

        //when
        Category categorSaved = categoryRepository.findById(category.getCategory_id()).get();
        categorSaved.setName("Enlatado");
        categorSaved.setDescription("Atún");
        Category categoryUpdated = categoryRepository.save(categorSaved);

        //then
        assertThat(categoryUpdated.getName()).isEqualTo("Enlatado");
        assertThat(categoryUpdated.getDescription()).isEqualTo("Atún");
    }

    @DisplayName("Test delete category")
    @Test
    void testDeleteCategory() {
        //given
        categoryRepository.save(category);

        //when
        categoryRepository.deleteById(category.getCategory_id());
        Optional<Category> categoryOptional = categoryRepository.findById(category.getCategory_id());

        //then
        assertThat(categoryOptional.isEmpty());
    }

}