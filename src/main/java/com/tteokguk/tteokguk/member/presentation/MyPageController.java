package com.tteokguk.tteokguk.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tteokguk.tteokguk.member.application.MyPageService;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RestController
@RequestMapping("/api/v1/auth/myPage")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping
	public ResponseEntity<MyPageResponse> getMyPageInfo(@RequestParam Long memberId) {
		MyPageResponse myPageInfo = myPageService.getMyPageInfo(memberId);
		return ResponseEntity.ok(myPageInfo);
	}
}
