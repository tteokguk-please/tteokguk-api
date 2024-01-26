package com.tteokguk.tteokguk.support.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SupportPageableRequest(
        @Positive(message = "페이지 번호는 양수입니다.")
        @NotNull(message = "페이지 번호는 필수입니다.")
        Integer page,

        @Min(value = 1, message = "페이지 수는 최소 1개 이상입니다.")
        @NotNull(message = "페이지 수는 필수입니다.")
        Integer size,
        
        @Min(value = 1, message = "페이지 번호 목록 갯수는 최소 1개 이상 입니다.")
        @Max(value = 10, message = "페이지 번호 목록 갯수는 최대 10개 입니다.")
        Integer count
) {
}
