package com.tteokguk.tteokguk.tteokguk.application.dto.response.assembler;

import com.tteokguk.tteokguk.support.application.dto.response.SupportTteokgukResponse;
import com.tteokguk.tteokguk.support.domain.Support;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.TteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class TteokgukResponseAssembler {
    public static TteokgukResponse toTteokgukResponse(Tteokguk tteokguk) {
        List<Ingredient> ingredients = tteokguk.getIngredients();
        List<Ingredient> usedIngredients = tteokguk.getUsedIngredients();
        List<Ingredient> requiredIngredients = ingredients.stream()
                .filter(ingredient -> !usedIngredients.contains(ingredient))
                .toList();

        try {
            return TteokgukResponse.builder()
                    .tteokgukId(tteokguk.getId())
                    .memberId(tteokguk.getMember().getId())
                    .nickname(tteokguk.getMember().getNickname())
                    .wish(tteokguk.getWish())
                    .ingredients(ingredients)
                    .usedIngredients(usedIngredients)
                    .requiredIngredients(requiredIngredients)
                    .access(tteokguk.isAccess())
                    .completion(tteokguk.isCompletion())
                    .backgroundColor(tteokguk.getBackgroundColor())
                    .frontGarnish(tteokguk.getVisibleIngredient1())
                    .backGarnish(tteokguk.getVisibleIngredient2())
                    .build();
        } catch (EntityNotFoundException e) {
            // 탈퇴한 사용자일 경우
            return TteokgukResponse.builder()
                    .tteokgukId(null)
                    .memberId(tteokguk.getMember().getId())
                    .nickname("탈퇴한 사용자")
                    .wish(tteokguk.getWish())
                    .ingredients(ingredients)
                    .usedIngredients(usedIngredients)
                    .requiredIngredients(requiredIngredients)
                    .access(tteokguk.isAccess())
                    .completion(tteokguk.isCompletion())
                    .backgroundColor(tteokguk.getBackgroundColor())
                    .frontGarnish(tteokguk.getVisibleIngredient1())
                    .backGarnish(tteokguk.getVisibleIngredient2())
                    .build();
        }
    }

    public static List<SupportTteokgukResponse> toSupportTteokgukResponses(List<Support> supports) {
        return supports.stream()
                .map(support -> new SupportTteokgukResponse(
                        support.getSupportedTteokguk().getId(),
                        support.getReceiver().getNickname(),
                        support.getSupportedTteokguk().isCompletion(),
                        support.getSupportedTteokguk().getUsedIngredients(),
                        support.getSupportedTteokguk().getBackgroundColor(),
                        support.getSupportedTteokguk().getVisibleIngredient1(),
                        support.getSupportedTteokguk().getVisibleIngredient2()
                ))
                .toList();
    }

    public static List<TteokgukResponse> toTteokgukResponses(List<Tteokguk> tteokguks) {
        return tteokguks.stream()
                .map(TteokgukResponseAssembler::toTteokgukResponse)
                .sorted(Comparator.comparing(TteokgukResponse::tteokgukId).reversed())
                .toList();
    }
}
