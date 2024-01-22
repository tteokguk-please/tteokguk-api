package com.tteokguk.tteokguk.tteokguk.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		@AuthenticationPrincipal UserDetails user,
		@RequestBody @Validated CreateTteokgukRequest request
	) {
		CreateTteokgukResponse response = tteokgukService.createTteokguk(user.getUsername(), request);
		return ResponseEntity.ok(response);
	}
}
