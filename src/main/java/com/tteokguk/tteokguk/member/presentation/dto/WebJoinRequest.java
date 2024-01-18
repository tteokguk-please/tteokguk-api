package com.tteokguk.tteokguk.member.presentation.dto;

import com.tteokguk.tteokguk.member.application.dto.AppJoinRequest;

// TODO: 요청 바디 검증이 필요하다.
public record WebJoinRequest(
	String email,
	String password,
	String nickname
) {

	public AppJoinRequest convert() {
		return new AppJoinRequest(email, password, nickname);
	}
}
