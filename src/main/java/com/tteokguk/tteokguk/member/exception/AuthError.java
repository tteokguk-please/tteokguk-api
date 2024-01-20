package com.tteokguk.tteokguk.member.exception;

import org.springframework.http.HttpStatus;

import com.tteokguk.tteokguk.global.exception.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthError implements ErrorCode {

	DUPLICATE_EMAIL("이메일이 중복되었습니다.", HttpStatus.BAD_REQUEST, "A001"),
	DUPLICATE_NICKNAME("닉네임이 중복되었습니다.", HttpStatus.BAD_REQUEST, "A002"),
	BAD_EMAIL("잘못된 이메일입니다.", HttpStatus.UNAUTHORIZED, "A003"),
	BAD_PASSWORD("잘못된 비밀번호입니다.", HttpStatus.UNAUTHORIZED, "A004"),
	;

	private final String message;
	private final HttpStatus status;
	private final String code;
}
