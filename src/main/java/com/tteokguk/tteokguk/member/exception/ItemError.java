package com.tteokguk.tteokguk.member.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.tteokguk.tteokguk.global.exception.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemError implements ErrorCode {

	INSUFFICIENT_INGREDIENTS("해당 사용자는 재료가 부족합니다.", BAD_REQUEST, "I_001"),
	INGREDIENTS_COUNT_CONFLICT("재료는 5개만 요청 가능합니다", BAD_REQUEST, "I_002"),
	;

	private final String message;
	private final HttpStatus status;
	private final String code;
}
