package com.dish.recipes.controller;

import com.dish.recipes.mapper.RecipesMapper;
import com.dish.recipes.message.RecipeMessage;
import com.dish.recipes.model.Recipe;
import com.dish.recipes.service.RecipesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipesControllerTest {

    @Mock
    private RecipesService recipesService;

    @Mock
    private RecipesMapper recipesMapper;

    @InjectMocks
    private final RecipesController testObj = new RecipesControllerImpl();

    @Test
    void searchRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(mock(Recipe.class));
        Page<Recipe> mock = new PageImpl(recipes);
        Mockito.when(recipesService.searchRecipes(
                ArgumentMatchers.eq(42),
                ArgumentMatchers.eq(1),
                ArgumentMatchers.eq("title"),
                ArgumentMatchers.eq(1),
                ArgumentMatchers.eq("content"),
                ArgumentMatchers.eq(true),
                ArgumentMatchers.anyList(),
                ArgumentMatchers.anyList()
        )).thenReturn(mock);
        Mockito.when(recipesMapper.toMessage(any(Recipe.class))).thenReturn(mock(RecipeMessage.class));
        testObj.searchRecipes(
                42,
                1,
                "title",
                1,
                "content",
                true,
                List.of("include"),
                List.of("exclude"));
        Mockito.verify(recipesMapper, times(1)).toMessage(any(Recipe.class));
    }

    @Test
    void createRecipe() {
        Mockito.when(recipesMapper.toEntity(any(RecipeMessage.class))).thenReturn(mock(Recipe.class));
        Mockito.when(recipesMapper.toMessage(any(Recipe.class))).thenReturn(mock(RecipeMessage.class));
        Mockito.when(recipesService.createRecipe(any(Recipe.class))).thenReturn(mock(Recipe.class));
        testObj.createRecipe(mock(RecipeMessage.class));
        Mockito.verify(recipesMapper, times(1)).toMessage(any(Recipe.class));
        Mockito.verify(recipesMapper, times(1)).toEntity(any(RecipeMessage.class));
        Mockito.verify(recipesService, times(1)).createRecipe(any(Recipe.class));
    }

    @Test
    void updateRecipe() {
        Mockito.when(recipesMapper.toEntity(any(RecipeMessage.class))).thenReturn(mock(Recipe.class));
        Mockito.when(recipesMapper.toMessage(any(Recipe.class))).thenReturn(mock(RecipeMessage.class));
        Mockito.when(recipesService.updateRecipe(any(Recipe.class))).thenReturn(mock(Recipe.class));
        testObj.updateRecipe(mock(RecipeMessage.class));
        Mockito.verify(recipesMapper, times(1)).toMessage(any(Recipe.class));
        Mockito.verify(recipesMapper, times(1)).toEntity(any(RecipeMessage.class));
        Mockito.verify(recipesService, times(1)).updateRecipe(any(Recipe.class));
    }

    @Test
    void deleteRecipe() {
        doNothing().when(recipesService).deleteRecipe(ArgumentMatchers.eq("title"));
        testObj.deleteRecipe("title");
        Mockito.verify(recipesService, times(1)).deleteRecipe(ArgumentMatchers.eq("title"));
    }
}