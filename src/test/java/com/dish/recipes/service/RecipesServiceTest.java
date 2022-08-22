package com.dish.recipes.service;

import com.dish.recipes.exception.BadRequestException;
import com.dish.recipes.exception.NotFoundException;
import com.dish.recipes.model.Category;
import com.dish.recipes.model.Ingredient;
import com.dish.recipes.model.Recipe;
import com.dish.recipes.repository.IngredientsRepository;
import com.dish.recipes.repository.RecipesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RecipesServiceTest {

    @AfterEach
    void tearDown() {
        recipesRepository.deleteAll();
        ingredientsRepository.deleteAll();
    }

    @Autowired
    private RecipesService recipesService;

    @Autowired
    private RecipesRepository recipesRepository;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Test
    void searchRecipes() {
        Recipe recipe = Recipe.builder()
                .title("Potato salad")
                .servings(1)
                .instructions("Some oven instruction")
                .build();
        Ingredient potato = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient schmotato = Ingredient.builder()
                .name("Salmon")
                .category(Category.MEAT)
                .build();
        recipe.getIngredients().add(potato);
        recipe.getIngredients().add(schmotato);
        recipesService.createRecipe(recipe);
        Ingredient justPotato = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient meat = Ingredient.builder()
                .name("Meat")
                .category(Category.MEAT)
                .build();
        Recipe recipe2 = Recipe.builder()
                .title("Just salad")
                .servings(2)
                .instructions("Some instruction 2")
                .build();
        recipe2.getIngredients().add(justPotato);
        recipe2.getIngredients().add(meat);
        recipesService.createRecipe(recipe2);
        Ingredient fish = Ingredient.builder()
                .name("Fish")
                .category(Category.MEAT)
                .build();
        Recipe recipe3 = Recipe.builder()
                .title("Fish")
                .servings(2)
                .instructions("Some instruction 3")
                .build();
        recipe3.getIngredients().add(fish);
        recipesService.createRecipe(recipe3);
        Ingredient vegetable0 = Ingredient.builder()
                .name("Vegetable0")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient vegetable1 = Ingredient.builder()
                .name("Vegetable1")
                .category(Category.VEGETARIAN)
                .build();
        Recipe recipe4 = Recipe.builder()
                .title("Vegetarian0")
                .servings(2)
                .instructions("Some instruction 4")
                .build();
        recipe4.getIngredients().add(vegetable0);
        recipe4.getIngredients().add(vegetable1);
        recipesService.createRecipe(recipe4);
        Ingredient vegetable2 = Ingredient.builder()
                .name("Vegetable0")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient vegetable3 = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Recipe recipe5 = Recipe.builder()
                .title("Vegetarian1")
                .servings(5)
                .instructions("Some oven instruction 4")
                .build();
        recipe5.getIngredients().add(vegetable2);
        recipe5.getIngredients().add(vegetable3);
        recipesService.createRecipe(recipe5);
        Page<Recipe> found;
        found = recipesService.searchRecipes(
                0,
                10,
                "Just salad",
                null,
                null,
                null,
                null,
                null
);
        assertEquals(1, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(5, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                null,
                null,
                null,
                List.of("Potato"),
                null);
        assertEquals(3, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                null,
                null,
                null,
                List.of("Fish", "Meat"),
                null);
        assertEquals(2, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                null,
                null,
                null,
                null,
                List.of("Meat"));
        assertEquals(4, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                null,
                null,
                true,
                null,
                null);
        assertEquals(2, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                null,
                null,
                true,
                null,
                List.of("Potato"));
        assertEquals(1, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                8,
                null,
                true,
                null,
                List.of("Potato"));
        assertEquals(0, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                null,
                "oven",
                true,
                null,
                null);
        assertEquals(1, found.stream().count());
        found = recipesService.searchRecipes(
                0,
                10,
                null,
                null,
                "oven",
                null,
                null,
                null);
        assertEquals(2, found.stream().count());
    }

    @Test
    void createRecipe() {
        Ingredient ingredient = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Recipe recipe = Recipe.builder()
                .title("Potato salad")
                .servings(1)
                .instructions("Some instruction")
                .build();
        recipe.getIngredients().add(ingredient);
        recipesService.createRecipe(recipe);
        Optional<Recipe> optionalRecipe = recipesRepository.findOneByTitle("Potato salad");
        assertTrue(optionalRecipe.isPresent());
        Ingredient duplicatedIngredient = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Recipe duplicatedRecipe = Recipe.builder()
                .title("Potato salad")
                .servings(2)
                .instructions("Some instruction 2")
                .build();
        duplicatedRecipe.getIngredients().add(duplicatedIngredient);
        assertThrows(BadRequestException.class, () -> recipesService.createRecipe(duplicatedRecipe));
    }

    @Test
    void updateRecipe() {
        Recipe recipe = Recipe.builder()
                .title("Potato salad")
                .servings(1)
                .instructions("Some instruction")
                .build();
        Ingredient potato = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient schmotato = Ingredient.builder()
                .name("Schmotato")
                .category(Category.MEAT)
                .build();
        Ingredient unknown = Ingredient.builder()
                .name("Unknown")
                .category(Category.MEAT)
                .build();
        recipe.getIngredients().add(potato);
        recipe.getIngredients().add(schmotato);
        recipe.getIngredients().add(unknown);
        recipesService.createRecipe(recipe);
        Ingredient justPotato = Ingredient.builder()
                .name("Just potato")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient schmotato2 = Ingredient.builder()
                .name("Schmotato")
                .category(Category.VEGETARIAN)
                .build();
        Recipe updated = Recipe.builder()
                .title("Potato salad not found")
                .servings(2)
                .instructions("Some instruction 2")
                .build();
        updated.getIngredients().add(justPotato);
        updated.getIngredients().add(schmotato2);
        assertThrowsExactly(NotFoundException.class, () -> recipesService.updateRecipe(updated));
        updated.setTitle("Potato salad");
        recipesService.updateRecipe(updated);
        Optional<Recipe> optionalRecipe = recipesRepository.findOneByTitle("Potato salad");
        assertTrue(optionalRecipe.isPresent());
        Recipe result = optionalRecipe.get();
        assertEquals(2, result.getServings());
        assertEquals(2, result.getIngredients().size());
        assertEquals("Just potato", result.getIngredients().get(0).getName());
    }

    @Test
    void deleteRecipe() {
        Recipe recipe = Recipe.builder()
                .title("Potato salad")
                .servings(1)
                .instructions("Some instruction")
                .build();
        Ingredient potato = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient schmotato = Ingredient.builder()
                .name("Schmotato")
                .category(Category.MEAT)
                .build();
        recipe.getIngredients().add(potato);
        recipe.getIngredients().add(schmotato);
        recipesService.createRecipe(recipe);
        Ingredient justPotato = Ingredient.builder()
                .name("Potato")
                .category(Category.VEGETARIAN)
                .build();
        Ingredient meat = Ingredient.builder()
                .name("Meat")
                .category(Category.MEAT)
                .build();
        Recipe recipe2 = Recipe.builder()
                .title("Just salad")
                .servings(2)
                .instructions("Some instruction 2")
                .build();
        recipe2.getIngredients().add(justPotato);
        recipe2.getIngredients().add(meat);
        recipesService.createRecipe(recipe2);
        assertEquals(2, recipesRepository.count());
        assertEquals(3, ingredientsRepository.count());
        recipesService.deleteRecipe("Potato salad");
        assertEquals(1, recipesRepository.count());
        recipesService.deleteRecipe("Just salad");
        assertEquals(0, recipesRepository.count());
    }

}
