package com.tteokguk.tteokguk.tteokguk.application.dto.response.assembler;

import com.tteokguk.tteokguk.support.application.dto.response.SupportTteokgukResponse;
import com.tteokguk.tteokguk.support.domain.Support;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.TteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TteokgukResponseAssembler {
    public static TteokgukResponse toTteokgukResponse(Tteokguk tteokguk) {
        return TteokgukResponse.builder()
                .tteokgukId(tteokguk.getId())
                .memberId(tteokguk.getMember().getId())
                .wish(tteokguk.getWish())
                .ingredients(tteokguk.getIngredients())
                .usedIngredients(tteokguk.getUsedIngredients())
                .access(tteokguk.isAccess())
                .completion(tteokguk.isCompletion())
                .build();
    }

    public static List<SupportTteokgukResponse> toSupportTteokgukResponses(List<Support> supports) {
        return supports.stream()
                .map(support -> new SupportTteokgukResponse(
                        support.getSupportedTteokguk().getId(),
                        support.getReceiver().getNickname(),
                        support.getSupportedTteokguk().isCompletion(),
                        support.getSupportedTteokguk().getUsedIngredients()
                ))
                .toList();
    }
}
