package com.tteokguk.tteokguk.support.application.dto;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import jakarta.validation.constraints.NotNull;

public record SupportRequest(
        @NotNull(message = "떡국 id가 유효하지 않습니다.") Long tteokgukId,
        boolean access,
        @NotNull(message = "응원 메시지가 유효하지 않습니다.") String message,
        @NotNull(message = "지원 재료가 유효하지 않습니다.") Ingredient supportIngredient
) {
}
