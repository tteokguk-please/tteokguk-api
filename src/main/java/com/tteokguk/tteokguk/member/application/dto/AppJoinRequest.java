package com.tteokguk.tteokguk.member.application.dto;

public record AppJoinRequest(
	String email,
	String password,
	String nickname
) {
}
