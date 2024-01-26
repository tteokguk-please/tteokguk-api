package com.tteokguk.tteokguk.tteokguk.application;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.IngredientRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.TteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.assembler.TteokgukResponseAssembler;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import com.tteokguk.tteokguk.tteokguk.infra.persistence.TteokgukRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.tteokguk.tteokguk.member.exception.MemberError.MEMBER_NOT_FOUND;
import static com.tteokguk.tteokguk.tteokguk.exception.TteokgukError.NOT_OWNER;

@Service
@Transactional
@RequiredArgsConstructor
public class TteokgukService {

    private final SimpleMemberRepository memberRepository;
    private final TteokgukRepository tteokgukRepository;

    public TteokgukResponse createTteokguk(
            Long id,
            CreateTteokgukRequest request
    ) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        List<Ingredient> ingredients = Ingredient.toIngredients(request.ingredients());

        Tteokguk savedTteokguk = tteokgukRepository.save(
                Tteokguk.of(request.wish(), ingredients, member, request.access()));

        member.addTteokguk(savedTteokguk);
        return TteokgukResponseAssembler.toTteokgukResponse(savedTteokguk);
    }

    public TteokgukResponse useIngredients(
            Long id,
            IngredientRequest request
    ) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        Tteokguk tteokguk = member.getTteokguks()
                .stream()
                .filter(t -> t.getId().equals(request.tteokgukId()))
                .findFirst()
                .orElseThrow(() -> BusinessException.of(NOT_OWNER));

        tteokguk.useIngredients(request.ingredients());

        return TteokgukResponseAssembler.toTteokgukResponse(tteokguk);
    }
}
