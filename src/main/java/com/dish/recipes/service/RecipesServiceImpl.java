package com.dish.recipes.service;

import com.dish.recipes.exception.BadRequestException;
import com.dish.recipes.exception.NotFoundException;
import com.dish.recipes.model.Category;
import com.dish.recipes.model.Ingredient;
import com.dish.recipes.model.Recipe;
import com.dish.recipes.repository.IngredientsRepository;
import com.dish.recipes.repository.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipesServiceImpl implements RecipesService {

    @Autowired
    private RecipesRepository recipesRepository;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    public Page<Recipe> searchRecipes(
            Integer page,
            Integer size,
            String title,
            Integer servings,
            String content,
            Boolean vegetarian,
            List<String> include,
            List<String> exclude) {
        Specification<Recipe> spec = buildSpecification(title, servings, content, vegetarian, include, exclude);
        return recipesRepository.findAll(spec, PageRequest.of(page, size));
    }

    private Specification<Recipe> buildSpecification(String title,
                                                     Integer servings,
                                                     String content,
                                                     Boolean vegetarian,
                                                     List<String> include,
                                                     List<String> exclude) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(title).ifPresent(t -> predicates.add(builder.equal(root.get("title"), t)));
            Optional.ofNullable(servings).ifPresent(s -> predicates.add(builder.equal(root.get("servings"), s)));
            Optional.ofNullable(content).ifPresent(s -> predicates.add(builder.like(root.get("instructions"),
                    "%" + s.toLowerCase() + "%")));
            Optional.ofNullable(vegetarian).ifPresent(v -> {
                if (v) {
                    Subquery<Long> subQuery = query.subquery(Long.class);
                    Join<Recipe, Ingredient> join = subQuery.from(Recipe.class).join("ingredients");
                    subQuery.select(join.getParent().get("id"));
                    subQuery.where(join.get("category").in(Category.MEAT));
                    Predicate p = root.get("id").in(subQuery.getSelection()).not();
                    predicates.add(p);
                }
            });
            Optional.ofNullable(include).ifPresent(i -> {
                if (!i.isEmpty()) {
                    Join<Recipe, Ingredient> join = root.join("ingredients");
                    predicates.add(join.get("name").in(i));
                }
            });
            Optional.ofNullable(exclude).ifPresent(e -> {
                if (!e.isEmpty()) {
                    Subquery<Long> subQuery = query.subquery(Long.class);
                    Join<Recipe, Ingredient> join = subQuery.from(Recipe.class).join("ingredients");
                    subQuery.select(join.getParent().get("id"));
                    subQuery.where(join.get("name").in(e));
                    Predicate p = root.get("id").in(subQuery.getSelection()).not();
                    predicates.add(p);
                }
            });
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Transactional
    public Recipe createRecipe(Recipe recipe) {
        if (recipesRepository.findOneByTitle(recipe.getTitle()).isPresent()) throw new BadRequestException();
        List<Ingredient> ingredients = recipe.getIngredients().stream()
                .map(i -> ingredientsRepository.findOneByName(i.getName()).orElse(i))
                .collect(Collectors.toList());
        recipe.setIngredients(ingredients);
        return recipesRepository.save(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Recipe updated) {
        List<Ingredient> ingredients = updated.getIngredients().stream()
                .map(i -> ingredientsRepository.findOneByName(i.getName()).orElse(i))
                .collect(Collectors.toList());
        return recipesRepository.findOneByTitle(updated.getTitle()).map(r -> {
            r.setInstructions(updated.getInstructions());
            r.setServings(updated.getServings());
            r.setIngredients(ingredients);
            return recipesRepository.save(r);
        }).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteRecipe(String title) {
        recipesRepository.deleteByTitle(title);
    }

}
