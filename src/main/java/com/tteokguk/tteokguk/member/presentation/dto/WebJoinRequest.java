package com.tteokguk.tteokguk.member.presentation.dto;

import com.tteokguk.tteokguk.member.application.dto.AppJoinRequest;

public record WebJoinRequest(
	String email,
	String password,
	String nickname
) {

	public AppJoinRequest convert() {
		return new AppJoinRequest(email, password, nickname);
	}
}
