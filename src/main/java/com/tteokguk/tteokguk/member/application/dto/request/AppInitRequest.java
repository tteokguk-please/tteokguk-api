package com.tteokguk.tteokguk.member.application.dto.request;

public record AppInitRequest(
	String nickname,
	Boolean acceptsMarketing
) {
}
