package com.dish.recipes.mapper;

import com.dish.recipes.message.IngredientMessage;
import com.dish.recipes.model.Category;
import com.dish.recipes.model.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class IngredientMapperTest {

    @Autowired
    private IngredientMapper ingredientMapper;

    @Test
    void toMessage() {
        Ingredient ingredient = Ingredient.builder()
                .id(42L)
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        IngredientMessage message = ingredientMapper.toMessage(ingredient);
        assertEquals("Potato", message.getName());
    }

    @Test
    void toEntity() {
        IngredientMessage ingredient = IngredientMessage.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient entity = ingredientMapper.toEntity(ingredient);
        assertNotNull(entity);
    }

}
