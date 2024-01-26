package com.tteokguk.tteokguk.member.exception;

import org.springframework.http.HttpStatus;

import com.tteokguk.tteokguk.global.exception.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthError implements ErrorCode {

	BAD_EMAIL("잘못된 이메일입니다.", HttpStatus.UNAUTHORIZED, "A_003"),
	BAD_PASSWORD("잘못된 비밀번호입니다.", HttpStatus.UNAUTHORIZED, "A_004"),
	INVALID_JWT_SIGNATURE("시그니처가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED, "A_005"),
	INVALID_JWT_TOKEN("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED, "A_006"),
	EXPIRED_JWT_TOKEN("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED, "A_007"),
	UNSUPPORTED_JWT_TOKEN("지원되지 않는 토큰입니다.", HttpStatus.UNAUTHORIZED, "A_008"),
	EMPTY_VALUE_IN_COOKIE("쿠키에 데이터가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED, "A_009"),
	UNUSABLE_TOKEN("사용할 수 없는 토큰입니다. 다시 로그인해주세요.", HttpStatus.BAD_REQUEST, "A_010");

	private final String message;
	private final HttpStatus status;
	private final String code;
}
