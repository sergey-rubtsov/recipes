package com.dish.recipes.repository;

import com.dish.recipes.model.Category;
import com.dish.recipes.model.Ingredient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class IngredientsRepositoryTest {

    @AfterEach
    void tearDown() {
        ingredientsRepository.deleteAll();
    }

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Test
    void saveAndDeleteIngredient() {
        Ingredient ingredient = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        ingredientsRepository.save(ingredient);
        assertEquals(1, ingredientsRepository.count());
        ingredientsRepository.deleteById(ingredient.getId());
        assertEquals(0, ingredientsRepository.count());
    }

    @Test
    void findOneByName() {
        Ingredient potato = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        ingredientsRepository.save(potato);
        Ingredient schmotato = Ingredient.builder()
                .name("Schmotato")
                .category(Category.MEAT)
                .build();
        ingredientsRepository.save(schmotato);
        assertTrue(ingredientsRepository.findOneByName("Schmotato").isPresent());
    }
}