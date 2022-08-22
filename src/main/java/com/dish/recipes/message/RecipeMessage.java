package com.dish.recipes.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class RecipeMessage {

    @Schema(description = "The recipe title is case sensitive and should be unique", required = true)
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String title;

    @Schema(description = "The number of servings, must be greater than zero", required = true)
    @JsonProperty(required = true)
    @Min(1)
    private Integer servings;

    @Schema(description = "The instructions contain quantitative measures of the ingredients and steps for " +
            "preparing the dish. It is possible to search for a keyword in the instruction text.", required = true)
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String instructions;

    @Schema(description = "The list of ingredients", required = true)
    @JsonProperty(required = true)
    @NotNull
    private List<IngredientMessage> ingredients;

}
