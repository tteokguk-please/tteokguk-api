package com.tteokguk.tteokguk.member.application;

import static com.tteokguk.tteokguk.member.exception.MemberError.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.application.dto.request.AppInitRequest;
import com.tteokguk.tteokguk.member.application.dto.response.AppInitResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.application.dto.response.UserInfoResponse;
import com.tteokguk.tteokguk.member.application.dto.response.assembler.UserInfoResponseAssembler;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.exception.MemberError;
import com.tteokguk.tteokguk.member.infra.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoService {

    private final MemberRepository memberRepository;

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

    public AppInitResponse initialize(Long memberId, AppInitRequest request) {
        if (memberRepository.existsByNickname(request.nickname()))
            throw new BusinessException(MemberError.DUPLICATE_EMAIL);

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        member.initialize(request.nickname(), request.acceptsMarketing());

        return AppInitResponse.of(member);
    }
}
