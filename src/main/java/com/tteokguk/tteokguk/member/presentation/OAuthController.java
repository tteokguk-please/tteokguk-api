package com.tteokguk.tteokguk.member.presentation;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.application.OAuthService;
import com.tteokguk.tteokguk.member.application.UserInfoService;
import com.tteokguk.tteokguk.member.application.dto.response.AppOAuthLoginResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.domain.ProviderType;
import com.tteokguk.tteokguk.member.exception.AuthError;
import com.tteokguk.tteokguk.member.presentation.dto.WebOAuthLoginRequest;
import com.tteokguk.tteokguk.member.presentation.dto.WebOAuthLoginResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthService oAuthService;
	private final UserInfoService userInfoService;

	@GetMapping("/{provider}/login")
	public String oAuthLogin(@PathVariable String provider, @RequestParam String code, HttpServletRequest request) {
		AppOAuthLoginResponse response = oAuthService.getByAuthorizationCode(
			ProviderType.valueOf(provider.toUpperCase()), code
		);

		String redirectUrl = Arrays.stream(request.getCookies())
			.filter(cookie -> "redirect_url".equals(cookie.getName()))
			.findFirst()
			.orElseThrow(() -> new BusinessException(AuthError.EMPTY_VALUE_IN_COOKIE))
			.getValue();

		final String url = String.format("%s?id=%s&accessToken=%s&refreshToken=%s&isInitialized=%s",
			redirectUrl, response.id(), response.accessToken(), response.refreshToken(), response.isInitialized());

		return "redirect:" + url;
	}

	@ResponseBody
	@PostMapping("/{provider}/login")
	public ResponseEntity<WebOAuthLoginResponse> oAuthLogin(
		@PathVariable String provider,
		@RequestBody WebOAuthLoginRequest request
	) {
		AppOAuthLoginResponse response = oAuthService.getByAccessToken(
			ProviderType.valueOf(provider.toUpperCase()), request.accessToken()
		);
		MyPageResponse myPageInfo = userInfoService.getMyPageInfo(response.id());
		return ResponseEntity.ok(WebOAuthLoginResponse.of(response, myPageInfo));
	}
}
