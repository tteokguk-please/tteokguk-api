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
	DUPLICATE_EMAIL("이메일이 중복되었습니다.", BAD_REQUEST, "M_002"),
	DUPLICATE_NICKNAME("닉네임이 중복되었습니다.", BAD_REQUEST, "M_003"),
	ALREADY_INITIALIZED_USER("이미 초기화된 유저입니다.", BAD_REQUEST, "M_004"),
	;

	private final String message;
	private final HttpStatus status;
	private final String code;
}
