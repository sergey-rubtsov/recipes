package com.dish.recipes.mapper;

import com.dish.recipes.message.IngredientMessage;
import com.dish.recipes.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = RecipesMapper.class)
public interface IngredientMapper {

    @Mapping(target = "id", ignore = true)
    Ingredient toEntity(IngredientMessage message);

    IngredientMessage toMessage(Ingredient entity);

    List<IngredientMessage> listToMessages(List<Ingredient> list);

    List<Ingredient> listToEntities(List<IngredientMessage> list);

}
