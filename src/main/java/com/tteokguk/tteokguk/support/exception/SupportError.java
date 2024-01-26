package com.tteokguk.tteokguk.support.exception;

import com.tteokguk.tteokguk.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum SupportError implements ErrorCode {

    MEMBER_NOT_FOUND("해당 사용자는 지원 내역이 없습니다.", NOT_FOUND, "S_001");

    private final String message;
    private final HttpStatus status;
    private final String code;
}
