package com.dish.recipes.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude="ingredients")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Integer servings;

    @Column(nullable = false)
    private String instructions;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(cascade={PERSIST, MERGE, REFRESH, DETACH}, fetch = FetchType.EAGER)
    private List<Ingredient> ingredients = new ArrayList<>();
}
