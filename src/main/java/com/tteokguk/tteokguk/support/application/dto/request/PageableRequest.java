package com.tteokguk.tteokguk.support.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PageableRequest(
        @Positive(message = "페이지 번호는 양수입니다.")
        @NotNull(message = "페이지 번호는 필수입니다.")
        Integer page,

        @Min(value = 1, message = "페이지 수는 최소 1개 이상입니다.")
        @NotNull(message = "페이지 수는 필수입니다.")
        Integer size
) {
}
