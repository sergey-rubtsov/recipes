package com.dish.recipes.controller;

import com.dish.recipes.message.RecipeMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RequestMapping(produces = "application/json")
public interface RecipesController {

    @GetMapping(produces = {"application/json"})
    Page<RecipeMessage> searchRecipes(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @Schema(description = "The recipe title is case sensitive and unique")
            @RequestParam(required = false) String title,
            @Schema(description = "The number of servings, must be greater than zero")
            @Min(1)
            @RequestParam(required = false) Integer servings,
            @Schema(description = "The key word to search in instructions")
            @RequestParam(required = false) String content,
            @Schema(description = "The category of recipe. If at least one ingredient is non-vegetarian, " +
                    "the entire recipe is considered non-vegetarian", allowableValues = {"true", "false"})
            @RequestParam(required = false) Boolean vegetarian,
            @Schema(description = "The list of included ingredients")
            @RequestParam(required = false) List<String> include,
            @Schema(description = "The list of excluded ingredients")
            @RequestParam(required = false) List<String> exclude);

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred")
    })
    ResponseEntity<RecipeMessage> createRecipe(@RequestBody RecipeMessage recipeMessage);

    @PutMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe successfully updated"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred")
    })
    ResponseEntity<RecipeMessage> updateRecipe(@RequestBody RecipeMessage recipeMessage);

    @DeleteMapping("/")
    void deleteRecipe(@RequestParam String title);

}
