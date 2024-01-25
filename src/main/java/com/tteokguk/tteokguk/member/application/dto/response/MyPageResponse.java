package com.tteokguk.tteokguk.member.application.dto.response;

import com.tteokguk.tteokguk.item.application.dto.response.ItemResponse;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.SimpleTteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.Builder;

import java.util.List;

@Builder
public record MyPageResponse(
        Long id,
        Ingredient primaryIngredient,
        String nickname,
        List<SimpleTteokgukResponse> tteokguks,
        List<ItemResponse> items
) {
}
