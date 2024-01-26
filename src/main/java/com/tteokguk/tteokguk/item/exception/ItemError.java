package com.tteokguk.tteokguk.item.exception;

import com.tteokguk.tteokguk.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ItemError implements ErrorCode {

    INSUFFICIENT_INGREDIENTS("해당 사용자는 재료가 부족합니다.", BAD_REQUEST, "I_001"),
    INGREDIENTS_COUNT_CONFLICT("재료는 5개만 요청 가능합니다.", BAD_REQUEST, "I_002"),
    UNNECESSARY_INGREDIENTS_REQUESTED("해당 떡국에 불필요한 재료가 요청되었습니다.", BAD_REQUEST, "I_003"),
    ALREADY_USED_INGREDIENTS("해당 떡국에 이미 사용된 재료가 다시 요청되었습니다.", BAD_REQUEST, "I_004");

    private final String message;
    private final HttpStatus status;
    private final String code;
}
