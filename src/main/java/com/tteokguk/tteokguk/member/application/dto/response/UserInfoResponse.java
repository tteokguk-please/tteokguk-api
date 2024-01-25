package com.tteokguk.tteokguk.member.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.application.dto.response.SimpleTteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.Builder;

import java.util.List;

@Builder
public record UserInfoResponse(
        String nickname,
        Ingredient primaryIngredient,
        List<SimpleTteokgukResponse> tteokguks
) {
}
