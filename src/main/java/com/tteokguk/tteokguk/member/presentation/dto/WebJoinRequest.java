package com.tteokguk.tteokguk.member.presentation.dto;

import org.hibernate.validator.constraints.Length;

import com.tteokguk.tteokguk.member.application.dto.AppJoinRequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record WebJoinRequest(
	@NotNull(message = "이메일을 반드시 입력해주세요.")
	@Pattern(
		regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$",
		message = "이메일 형식을 지켜주세요.")
	String email,
	@Length(min = 8, max = 16, message = "비밀번호를 8 ~ 16글자로 입력해주세요.")
	@Pattern(
		regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).*",
		message = "영문, 숫자, 특수문자가 반드시 조합되어야 합니다.")
	String password,
	@Length(min = 2, max = 6, message = "닉네임을 2 ~ 6글자로 입력해주세요.")
	String nickname
) {

	public AppJoinRequest convert() {
		return new AppJoinRequest(email, password, nickname);
	}
}
