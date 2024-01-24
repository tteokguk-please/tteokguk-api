package com.tteokguk.tteokguk.member.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.Builder;

import java.util.List;

@Builder
public record TteokgukResponse(
        Long tteokgukId,
        String wish,
        boolean access,
        List<Ingredient> tteokgukIngredients
) {
}
