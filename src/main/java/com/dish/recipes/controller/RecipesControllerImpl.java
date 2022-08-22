package com.dish.recipes.controller;

import com.dish.recipes.mapper.RecipesMapper;
import com.dish.recipes.message.RecipeMessage;
import com.dish.recipes.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipesControllerImpl implements RecipesController {

    @Autowired
    private RecipesService recipesService;

    @Autowired
    private RecipesMapper recipesMapper;

    @Override
    public Page<RecipeMessage> searchRecipes(
            Integer page,
            Integer size,
            String title,
            Integer servings,
            String content,
            Boolean vegetarian,
            List<String> include,
            List<String> exclude) {
        return recipesService.searchRecipes(page, size, title, servings, content, vegetarian, include, exclude)
                .map(recipe -> recipesMapper.toMessage(recipe));
    }

    @Override
    public ResponseEntity<RecipeMessage> createRecipe(RecipeMessage recipeMessage) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipesMapper.toMessage(
                recipesService.createRecipe(recipesMapper.toEntity(recipeMessage))));
    }

    @Override
    public ResponseEntity<RecipeMessage> updateRecipe(RecipeMessage recipeMessage) {
        return ResponseEntity.ok(recipesMapper.toMessage(
                recipesService.createRecipe(recipesMapper.toEntity(recipeMessage))));
    }

    @Override
    public void deleteRecipe(String title) {
        recipesService.deleteRecipe(title);
    }

}
