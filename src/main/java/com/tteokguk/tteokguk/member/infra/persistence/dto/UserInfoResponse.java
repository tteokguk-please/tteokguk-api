package com.tteokguk.tteokguk.member.infra.persistence.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfoResponse(
	@JsonProperty("id")
	String id,
	@JsonProperty("connected_at")
	@JsonFormat(
		shape = JsonFormat.Shape.STRING,
		pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
		timezone = "Asia/Seoul"
	)
	LocalDateTime connectedAt
) {
}
