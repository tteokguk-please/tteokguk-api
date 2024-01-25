package com.tteokguk.tteokguk.member.application.dto.request;

public record AppJoinRequest(
	String email,
	String password,
	String nickname,
	Boolean acceptsMarketing
) {
}
