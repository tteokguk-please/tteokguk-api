package com.tteokguk.tteokguk.support.application.dto.response.assembler;

import com.tteokguk.tteokguk.support.application.dto.response.SupportResponse;
import com.tteokguk.tteokguk.support.domain.Support;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SupportResponseAssembler {
    public static SupportResponse toSupportResponse(Support support) {
        return SupportResponse.builder()
                .id(support.getId())
                .rewardIngredient(support.getRewardIngredient())
                .rewardQuantity(support.getRewardQuantity())
                .build();
    }
}
