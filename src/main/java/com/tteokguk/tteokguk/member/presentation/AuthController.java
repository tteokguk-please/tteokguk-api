package com.tteokguk.tteokguk.member.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tteokguk.tteokguk.member.application.AuthService;
import com.tteokguk.tteokguk.member.application.RefreshTokenService;
import com.tteokguk.tteokguk.member.application.UserInfoService;
import com.tteokguk.tteokguk.member.application.dto.response.AppIssuedTokensResponse;
import com.tteokguk.tteokguk.member.application.dto.response.AppJoinResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.presentation.dto.WebCheckEmailRequest;
import com.tteokguk.tteokguk.member.presentation.dto.WebCheckNicknameRequest;
import com.tteokguk.tteokguk.member.presentation.dto.WebExistedResourceResponse;
import com.tteokguk.tteokguk.member.presentation.dto.WebIssuedTokensRequest;
import com.tteokguk.tteokguk.member.presentation.dto.WebIssuedTokensResponse;
import com.tteokguk.tteokguk.member.presentation.dto.WebJoinRequest;
import com.tteokguk.tteokguk.member.presentation.dto.WebJoinResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final UserInfoService userInfoService;
	private final RefreshTokenService refreshTokenService;

	@PostMapping("/join")
	public ResponseEntity<WebJoinResponse> join(@RequestBody @Validated WebJoinRequest request) {
		AppJoinResponse appResponse = authService.join(request.convert());
		return ResponseEntity.created(URI.create("/api/v1/members/" + appResponse.id()))
			.body(WebJoinResponse.of(appResponse));
	}

	@GetMapping("/check-email/{email}")
	public ResponseEntity<WebExistedResourceResponse> checkEmail(@Validated WebCheckEmailRequest request) {
		boolean isExist = authService.existsByEmail(request.email());
		return ResponseEntity.ok(new WebExistedResourceResponse(isExist));
	}

	@GetMapping("/check-nickname/{nickname}")
	public ResponseEntity<WebExistedResourceResponse> checkNickname(@Validated WebCheckNicknameRequest request) {
		boolean isExist = authService.existsByNickname(request.nickname());
		return ResponseEntity.ok(new WebExistedResourceResponse(isExist));
	}

	@PostMapping("/token")
	public ResponseEntity<WebIssuedTokensResponse> reIssueTokens(@RequestBody WebIssuedTokensRequest request) {
		AppIssuedTokensResponse issuedTokensResponse = refreshTokenService.issueTokens(request.refreshToken());
		MyPageResponse myInfoResponse = userInfoService.getMyPageInfo(issuedTokensResponse.id());
		return ResponseEntity.ok(
			WebIssuedTokensResponse.of(issuedTokensResponse, myInfoResponse)
		);
	}
}
