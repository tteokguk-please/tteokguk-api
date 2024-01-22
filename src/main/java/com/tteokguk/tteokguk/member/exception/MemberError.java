package com.tteokguk.tteokguk.member.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.tteokguk.tteokguk.global.exception.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberError implements ErrorCode {

	MEMBER_NOT_FOUND("해당 사용자를 찾을 수 없습니다.", NOT_FOUND, "M_001"),
	MEMBER_INGREDIENT_EXCEPTION("식별할 수 없는 재료가 요청되었습니다.", BAD_REQUEST, "M_002");

	private final String message;
	private final HttpStatus status;
	private final String code;
}
