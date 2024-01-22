package com.tteokguk.tteokguk.tteokguk.application.dto.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

public record CreateTteokgukRequest(
	@NotNull(message = "소원이 유효하지 않아요.")
	@Length(max = 150, message = "문자열의 최대 길이는 150자입니다.")
	String wish,

	@NotNull(message = "떡국 재료가 유효하지 않아요.")
	List<String> ingredients
) {
}
