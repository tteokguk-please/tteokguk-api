package com.tteokguk.tteokguk.tteokguk.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.tteokguk.application.TteokgukService;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.CreateTteokgukResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tteokguk")
public class TteokgukController {

	private final TteokgukService tteokgukService;

	@PostMapping
	public ResponseEntity<CreateTteokgukResponse> createTteokguk(
		@AuthenticationPrincipal Member member,
		@RequestBody @Validated CreateTteokgukRequest request
	) {
		log.warn("{}", member.getId());
		CreateTteokgukResponse response = tteokgukService.createTteokguk(request);
		return ResponseEntity.ok(response);
	}
}
