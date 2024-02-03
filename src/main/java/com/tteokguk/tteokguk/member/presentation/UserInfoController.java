package com.tteokguk.tteokguk.member.presentation;

import java.util.List;

import com.tteokguk.tteokguk.global.dto.response.ApiPageResponse;
import com.tteokguk.tteokguk.global.security.annotation.AuthId;
import com.tteokguk.tteokguk.member.application.UserInfoService;
import com.tteokguk.tteokguk.member.application.dto.response.AppInitResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MemberResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.application.dto.response.UserInfoResponse;
import com.tteokguk.tteokguk.member.presentation.dto.WebInitRequest;
import com.tteokguk.tteokguk.member.presentation.dto.WebInitResponse;
import com.tteokguk.tteokguk.support.application.dto.request.PageableRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/myPage")
    public ResponseEntity<MyPageResponse> getMyPageInfo(@AuthId Long id) {
        MyPageResponse myPageInfo = userInfoService.getMyPageInfo(id);
        return ResponseEntity.ok(myPageInfo);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @AuthId Long id,
            @PathVariable Long userId
    ) {
        UserInfoResponse userInfo = userInfoService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/random")
    public ResponseEntity<UserInfoResponse> getRandomUserInfo(@AuthId Long id) {
        UserInfoResponse userInfo = userInfoService.getRandomUserInfo();
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/initialization")
    public ResponseEntity<WebInitResponse> initialize(
            @AuthId Long id,
            @RequestBody WebInitRequest request
    ) {
        AppInitResponse response = userInfoService.initialize(id, request.convert());
        return ResponseEntity.ok(WebInitResponse.of(response));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> delete(@AuthId Long id) {
        userInfoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-nickname/{nickname}")
    public ResponseEntity<UserInfoResponse> getUserInfoByNickname(@PathVariable String nickname) {
        UserInfoResponse userInfo = userInfoService.getUserInfoByNickname(nickname);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<MemberResponse>> searchByNickname(
        @RequestParam String nickname, @Valid PageableRequest pageableRequest
    ) {
        ApiPageResponse<MemberResponse> response =
            userInfoService.getMembersByNickname(nickname, pageableRequest.page(), pageableRequest.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberResponse>> searchAllByNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userInfoService.getAllMembersByNickname(nickname));
    }
}
