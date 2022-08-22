package com.dish.recipes.mapper;

import com.dish.recipes.message.RecipeMessage;
import com.dish.recipes.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IngredientMapper.class)
public interface RecipesMapper {

    @Mapping(target = "id", ignore = true)
    Recipe toEntity(RecipeMessage message);

    RecipeMessage toMessage(Recipe entity);

}
