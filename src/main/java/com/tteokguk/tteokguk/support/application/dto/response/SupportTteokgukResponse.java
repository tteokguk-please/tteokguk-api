package com.tteokguk.tteokguk.support.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.BackgroundColor;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import java.util.List;

public record SupportTteokgukResponse(
        Long tteokgukId,
        String receiverNickname,
        Boolean completion,
        List<?> ingredients,
        BackgroundColor backgroundColor,
        Ingredient visibleIngredient1,
        Ingredient visibleIngredient2
) {
}
