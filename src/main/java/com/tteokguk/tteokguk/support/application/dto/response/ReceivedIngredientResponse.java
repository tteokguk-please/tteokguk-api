package com.tteokguk.tteokguk.support.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

public record ReceivedIngredientResponse(
        Long id,
        Long senderId,
        String nickname,
        Ingredient ingredient,
        String message,
        boolean access,
		Long supportedTteokgukId
) {
}
