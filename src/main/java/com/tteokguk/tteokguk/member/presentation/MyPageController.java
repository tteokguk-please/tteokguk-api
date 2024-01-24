package com.tteokguk.tteokguk.member.presentation;

import com.tteokguk.tteokguk.member.application.MyPageService;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/myPage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<MyPageResponse> getMyPageInfo(@RequestParam Long memberId) {
        MyPageResponse myPageInfo = myPageService.getMyPageInfo(memberId);
        return ResponseEntity.ok(myPageInfo);
    }
}
