package com.dish.recipes.repository;

import com.dish.recipes.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findOneByName(String name);

}
