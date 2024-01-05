package com.tteokguk.tteokguk.global.exception.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalError implements ErrorCode {

	GLOBAL_NOT_FOUND("Not Found", NOT_FOUND, "G_001"),
	INVALID_REQUEST_PARAM("Invalid Request Parameter", BAD_REQUEST, "G_002");

	private final String message;
	private final HttpStatus status;
	private final String code;
}
