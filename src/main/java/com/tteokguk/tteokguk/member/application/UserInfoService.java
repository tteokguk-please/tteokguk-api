package com.tteokguk.tteokguk.member.application;

import static com.tteokguk.tteokguk.member.exception.MemberError.*;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tteokguk.tteokguk.global.dto.response.ApiPageResponse;
import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.global.security.jwt.Jwt;
import com.tteokguk.tteokguk.global.security.jwt.JwtService;
import com.tteokguk.tteokguk.member.application.dto.request.AppInitRequest;
import com.tteokguk.tteokguk.member.application.dto.response.AppInitResponse;
import com.tteokguk.tteokguk.member.application.dto.response.AppMyIngredientResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MemberResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.application.dto.response.UserInfoResponse;
import com.tteokguk.tteokguk.member.application.dto.response.assembler.UserInfoResponseAssembler;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public MyPageResponse getMyPageInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        return UserInfoResponseAssembler.transferToMyPageResponse(member);
    }

    public UserInfoResponse getUserInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        return UserInfoResponseAssembler.transferToUserInfoResponse(member);
    }

    public UserInfoResponse getRandomUserInfo() {
        Member randomUser = memberRepository.findRandomUser();
        return UserInfoResponseAssembler.transferToUserInfoResponse(randomUser);
    }

    public AppInitResponse initialize(Long memberId, AppInitRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        if (member.isInitialized())
            throw new BusinessException(ALREADY_INITIALIZED_USER);

        if (memberRepository.existsByNickname(request.nickname()))
            throw new BusinessException(DUPLICATE_NICKNAME);

        member.initialize(request.nickname(), request.acceptsMarketing());

        Long now = System.currentTimeMillis();
        Jwt accessToken = jwtService.getAccessToken(member, now);
        Jwt refreshToken = jwtService.getRefreshToken(member, now);

        return AppInitResponse.of(member, accessToken.getEncodedBody(), refreshToken.getEncodedBody());
    }

    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        member.delete();
    }

    public UserInfoResponse getUserInfoByNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
            .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        return UserInfoResponseAssembler.transferToUserInfoResponse(member);
    }

    public ApiPageResponse<MemberResponse> getMembersByNickname(String nickname, int page, int size) {
        if (!StringUtils.hasText(nickname)) {
            return ApiPageResponse.of(new PageImpl<>(Collections.emptyList()));
        }

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Member> members = memberRepository.findByNicknameStartingWith(nickname, pageable);

        List<MemberResponse> content =
            members.map(UserInfoResponseAssembler::transferToMemberResponse).getContent();
        return ApiPageResponse.of(new PageImpl<>(content, pageable, content.size()));
    }

    public List<MemberResponse> getAllMembersByNickname(String nickname) {
        if (!StringUtils.hasText(nickname))
            return Collections.emptyList();

        List<Member> members = memberRepository.findAllByNicknameStartingWith(nickname);

        return members.stream()
            .map(UserInfoResponseAssembler::transferToMemberResponse)
            .toList();
    }

    public AppMyIngredientResponse getMyIngredients(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        return AppMyIngredientResponse.of(member);
    }
}
