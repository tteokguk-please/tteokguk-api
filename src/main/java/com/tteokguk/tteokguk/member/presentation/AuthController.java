package com.tteokguk.tteokguk.member.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tteokguk.tteokguk.member.application.AuthService;
import com.tteokguk.tteokguk.member.presentation.dto.WebJoinRequest;
import com.tteokguk.tteokguk.member.presentation.dto.WebJoinResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService memberService;

	@PostMapping("/join")
	public ResponseEntity<WebJoinResponse> join(@RequestBody @Validated WebJoinRequest request) {
		Long savedId = memberService.join(request.convert());
		return ResponseEntity.created(URI.create("/api/v1/members/" + savedId)).body(new WebJoinResponse(savedId));
	}
}
