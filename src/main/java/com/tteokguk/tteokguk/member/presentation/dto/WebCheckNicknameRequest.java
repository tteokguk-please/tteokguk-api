package com.tteokguk.tteokguk.member.presentation.dto;

import org.hibernate.validator.constraints.Length;

public record WebCheckNicknameRequest(
	@Length(min = 2, max = 6, message = "닉네임을 2 ~ 6글자로 입력해주세요.")
	String nickname
) {
}
