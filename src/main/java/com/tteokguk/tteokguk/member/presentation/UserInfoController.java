package com.tteokguk.tteokguk.member.presentation;

import com.tteokguk.tteokguk.member.application.UserInfoService;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.application.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/myPage")
    public ResponseEntity<MyPageResponse> getMyPageInfo(@AuthenticationPrincipal UserDetails user) {
        MyPageResponse myPageInfo = userInfoService.getMyPageInfo(Long.parseLong(user.getUsername()));
        return ResponseEntity.ok(myPageInfo);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable Long userId) {
        UserInfoResponse userInfo = userInfoService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }
}
