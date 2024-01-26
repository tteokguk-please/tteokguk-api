package com.tteokguk.tteokguk.support.application;


import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.MemberRepository;
import com.tteokguk.tteokguk.support.application.dto.SupportRequest;
import com.tteokguk.tteokguk.support.application.dto.response.SupportResponse;
import com.tteokguk.tteokguk.support.application.dto.response.assembler.SupportResponseAssembler;
import com.tteokguk.tteokguk.support.domain.Support;
import com.tteokguk.tteokguk.support.infra.persistence.SupportRepository;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import com.tteokguk.tteokguk.tteokguk.infra.persistence.TteokgukRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tteokguk.tteokguk.member.exception.MemberError.MEMBER_NOT_FOUND;
import static com.tteokguk.tteokguk.tteokguk.exception.TteokgukError.TTEOKGUK_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class SupportService {

    private final SupportRepository supportRepository;
    private final MemberRepository memberRepository;
    private final TteokgukRepository tteokgukRepository;

    public SupportResponse doSupport(
            Long id,
            SupportRequest request
    ) {
        Member sender = memberRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        Tteokguk supportedTteokguk = tteokgukRepository.findById(request.tteokgukId())
                .orElseThrow(() -> BusinessException.of(TTEOKGUK_NOT_FOUND));

        Member receiver = supportedTteokguk.getMember();

        Support support = Support.of(
                sender, receiver, supportedTteokguk,
                request.supportIngredient(), request.access(), request.message()
        );
        
        Support savedSupport = supportRepository.save(support);
        return SupportResponseAssembler.toSupportResponse(savedSupport);
    }
}
