package com.dish.recipes.service;

import com.dish.recipes.model.Recipe;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipesService {

    Page<Recipe> searchRecipes(
            Integer page,
            Integer size,
            String title,
            Integer servings,
            String content,
            Boolean vegetarian,
            List<String> include,
            List<String> exclude);

    Recipe createRecipe(Recipe recipe);

    Recipe updateRecipe(Recipe updated);

    void deleteRecipe(String title);

}
