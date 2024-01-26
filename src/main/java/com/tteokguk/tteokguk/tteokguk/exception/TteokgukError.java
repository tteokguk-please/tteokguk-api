package com.tteokguk.tteokguk.tteokguk.exception;

import com.tteokguk.tteokguk.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum TteokgukError implements ErrorCode {

    TTEOKGUK_NOT_FOUND("해당 떡국을 찾을 수 없습니다.", NOT_FOUND, "T_001"),
    NOT_OWNER("해당 떡국의 주인이 아닙니다.", BAD_REQUEST, "T_002");

    private final String message;
    private final HttpStatus status;
    private final String code;
}
