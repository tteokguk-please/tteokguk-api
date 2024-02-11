package com.tteokguk.tteokguk.tteokguk.application;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.application.dto.response.SupporterResponse;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.MemberRepository;
import com.tteokguk.tteokguk.support.application.dto.request.PageableRequest;
import com.tteokguk.tteokguk.support.domain.Support;
import com.tteokguk.tteokguk.support.infra.persistence.SupportRepository;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.IngredientRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.TteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.assembler.TteokgukResponseAssembler;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import com.tteokguk.tteokguk.tteokguk.infra.persistence.TteokgukRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.tteokguk.tteokguk.member.exception.MemberError.MEMBER_NOT_FOUND;
import static com.tteokguk.tteokguk.tteokguk.exception.TteokgukError.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TteokgukService {

    private final MemberRepository memberRepository;
    private final TteokgukRepository tteokgukRepository;
    private final SupportRepository supportRepository;

    public TteokgukResponse createTteokguk(
            Long id,
            CreateTteokgukRequest request
    ) {
        Member member = memberRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        List<Ingredient> ingredients = Ingredient.toIngredients(request.ingredients());
        List<Ingredient> mutableIngredients = new ArrayList<>(ingredients);
        Collections.shuffle(mutableIngredients);

        Tteokguk savedTteokguk = tteokgukRepository.save(
                Tteokguk.of(request.wish(), mutableIngredients, member, request.access()));

        member.addTteokguk(savedTteokguk);
        return TteokgukResponseAssembler.toTteokgukResponse(savedTteokguk);
    }

    public TteokgukResponse getRandomTteokguk() {
        Tteokguk randomTteokguk = tteokgukRepository.findRandomTteokguk();
        return TteokgukResponseAssembler.toTteokgukResponse(randomTteokguk);
    }

    public void deleteTteokguk(
            Long id,
            Long tteokgukId
    ) {
        Member member = memberRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        Tteokguk tteokguk = member.getTteokguks().stream()
                .filter(t -> t.getId().equals(tteokgukId))
                .findFirst()
                .orElseThrow(() -> BusinessException.of(NOT_OWNER));

        tteokguk.delete();
    }

    public TteokgukResponse doCompletion(
            Long id,
            Long tteokgukId
    ) {
        Member member = memberRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        Tteokguk tteokguk = member.getTteokguks().stream()
                .filter(t -> t.getId().equals(tteokgukId))
                .findFirst()
                .orElseThrow(() -> BusinessException.of(NOT_OWNER));

        tteokguk.updateCompletion();
        return TteokgukResponseAssembler.toTteokgukResponse(tteokguk);
    }

    public TteokgukResponse useIngredients(
            Long id,
            IngredientRequest request
    ) {
        Member member = memberRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        Tteokguk tteokguk = member.getTteokguks()
                .stream()
                .filter(t -> t.getId().equals(request.tteokgukId()))
                .findFirst()
                .orElseThrow(() -> BusinessException.of(NOT_OWNER));

        tteokguk.useIngredients(request.ingredients());

        return TteokgukResponseAssembler.toTteokgukResponse(tteokguk);
    }

    public Page<TteokgukResponse> findNewTteokguks(Long memberId, PageableRequest request) {
        PageRequest pageable = PageRequest.of(
                request.page() - 1,
                request.size(),
                Sort.by(DESC, "id"));

        List<Tteokguk> newTteokguks = findNewTteoguks(memberId, pageable);
        List<TteokgukResponse> responses = TteokgukResponseAssembler.toTteokgukResponses(newTteokguks);
        responses.forEach(response -> log.warn("{}", response));
        return new PageImpl<>(responses, pageable, responses.size());
    }

    private List<Tteokguk> findNewTteoguks(Long memberId, PageRequest pageable) {
        if (memberId == null)
            return tteokgukRepository.findNewTteokguks(pageable);
        else
            return tteokgukRepository.findNewTteokguks(memberId, pageable);
    }

    public Page<TteokgukResponse> findCompletionTteokguks(Long memberId, PageableRequest request) {
        PageRequest pageable = PageRequest.of(
                request.page() - 1,
                request.size());

        List<Tteokguk> completionTteokguks = findCompletionTteokguks(memberId, pageable);
        List<TteokgukResponse> responses = TteokgukResponseAssembler.toTteokgukResponses(completionTteokguks);
        responses.forEach(response -> log.warn("{}", response));
        return new PageImpl<>(responses, pageable, responses.size());
    }

    private List<Tteokguk> findCompletionTteokguks(Long memberId, PageRequest pageable) {
        if (memberId == null)
            return tteokgukRepository.findCompletionTteokguks(pageable);
        else
            return tteokgukRepository.findCompletionTteokguks(memberId, pageable);
    }

    public TteokgukResponse getTteokguk(Long memberId, Long tteokgukId) {
        Tteokguk tteokguk = tteokgukRepository.findByIdAndDeleted(tteokgukId, false)
                .orElseThrow(() -> BusinessException.of(TTEOKGUK_NOT_FOUND));

        if (!tteokguk.isAccess() && !Objects.equals(tteokguk.getMember().getId(), memberId)) {
            throw BusinessException.of(NOT_ALLOWED);
        }
        return TteokgukResponseAssembler.toTteokgukResponse(tteokguk);
    }

    public SupporterResponse findSupporters(Long tteokgukId) {
        Tteokguk tteokguk = tteokgukRepository.findById(tteokgukId)
            .orElseThrow(() -> BusinessException.of(TTEOKGUK_NOT_FOUND));

        List<Support> supports = supportRepository.findAllBySupportedTteokguk(tteokguk);

        return SupporterResponse.of(tteokguk, supports);
    }
}
