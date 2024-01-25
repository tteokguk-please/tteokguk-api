package com.tteokguk.tteokguk.member.application;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.application.dto.response.UserInfoResponse;
import com.tteokguk.tteokguk.member.application.dto.response.assembler.UserInfoResponseAssembler;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tteokguk.tteokguk.member.exception.MemberError.MEMBER_NOT_FOUND;

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
}