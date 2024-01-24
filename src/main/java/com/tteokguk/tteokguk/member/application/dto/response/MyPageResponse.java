package com.tteokguk.tteokguk.member.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.Builder;

import java.util.List;

@Builder
public record MyPageResponse(
        Long id,
        Ingredient primaryIngredient,
        String nickname,
        List<TteokgukResponse> tteokguks,
        List<ItemResponse> items
) {
}
