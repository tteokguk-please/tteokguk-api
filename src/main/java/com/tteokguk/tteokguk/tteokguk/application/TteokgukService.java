package com.tteokguk.tteokguk.tteokguk.application;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.CreateTteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import com.tteokguk.tteokguk.tteokguk.infra.persistence.TteokgukRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.tteokguk.tteokguk.member.exception.MemberError.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class TteokgukService {

    private final SimpleMemberRepository memberRepository;
    private final TteokgukRepository tteokgukRepository;

    public CreateTteokgukResponse createTteokguk(
            String email,
            CreateTteokgukRequest request
    ) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        List<Ingredient> ingredients = Ingredient.toIngredients(request.ingredients());
        Tteokguk tteokguk = Tteokguk.of(request.wish(), ingredients, member, request.access());
        Tteokguk savedTteokguk = tteokgukRepository.save(tteokguk);

        member.addTteokguk(savedTteokguk);

        return CreateTteokgukResponse.builder()
                .tteokgukId(savedTteokguk.getId())
                .memberId(savedTteokguk.getMember().getId())
                .wish(savedTteokguk.getWish())
                .ingredients(savedTteokguk.getIngredients())
                .access(savedTteokguk.isAccess())
                .completion(savedTteokguk.isCompletion())
                .build();
    }
}
