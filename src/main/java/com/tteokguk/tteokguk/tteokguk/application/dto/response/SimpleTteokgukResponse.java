package com.tteokguk.tteokguk.tteokguk.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.BackgroundColor;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.Builder;

import java.util.List;

@Builder
public record SimpleTteokgukResponse(
        Long tteokgukId,
        String wish,
        boolean access,
        boolean completion,
        List<Ingredient> ingredients,
        BackgroundColor backgroundColor,
        Ingredient visibleIngredient1,
        Ingredient visibleIngredient2
) {
}
