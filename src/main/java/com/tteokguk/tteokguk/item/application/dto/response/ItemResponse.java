package com.tteokguk.tteokguk.item.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.Builder;

@Builder
public record ItemResponse(
        Ingredient ingredient,
        int stockQuantity
) {
}
