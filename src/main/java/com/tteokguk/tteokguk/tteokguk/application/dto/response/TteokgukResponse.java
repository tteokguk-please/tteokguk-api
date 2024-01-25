package com.tteokguk.tteokguk.tteokguk.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.Builder;

import java.util.List;

@Builder
public record TteokgukResponse(
        Long tteokgukId,
        Long memberId,
        String wish,
        boolean access,
        boolean completion,
        List<Ingredient> ingredients
) {
}
