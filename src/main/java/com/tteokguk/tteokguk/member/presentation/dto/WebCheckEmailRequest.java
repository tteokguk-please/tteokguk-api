package com.tteokguk.tteokguk.member.presentation.dto;

import jakarta.validation.constraints.Pattern;

public record WebCheckEmailRequest(
	@Pattern(
		regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$",
		message = "이메일 형식을 지켜주세요.")
	String email
) {

}
