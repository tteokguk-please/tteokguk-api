package com.tteokguk.tteokguk.support.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.Builder;

@Builder
public record SupportResponse(
        Long id,
        Ingredient rewardIngredient,
        int rewardQuantity
) {
}
