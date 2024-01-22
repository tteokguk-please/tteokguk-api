package com.tteokguk.tteokguk.tteokguk.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.tteokguk.tteokguk.global.exception.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TteokgukError implements ErrorCode {

	TTEOKGUK_NOT_FOUND("해당 떡국을 찾을 수 없습니다.", NOT_FOUND, "T_001");

	private final String message;
	private final HttpStatus status;
	private final String code;
}
