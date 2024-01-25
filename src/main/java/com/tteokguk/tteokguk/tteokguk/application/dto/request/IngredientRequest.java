package com.tteokguk.tteokguk.tteokguk.application.dto.request;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record IngredientRequest(
        @NotNull(message = "tteokgukId가 유효하지 않아요.")
        Long tteokgukId,

        @NotNull(message = "재료가 유효하지 않아요.")
        @NotEmpty(message = "재료가 유효하지 않아요.")
        List<Ingredient> ingredients
) {
}
