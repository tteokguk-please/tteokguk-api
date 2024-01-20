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
	NOT_FOUND_EMAIL("이메일에 해당하는 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "A003");

	private final String message;
	private final HttpStatus status;
	private final String code;
}
