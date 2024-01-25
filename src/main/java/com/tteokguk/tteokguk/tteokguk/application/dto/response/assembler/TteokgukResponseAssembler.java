package com.tteokguk.tteokguk.tteokguk.application.dto.response.assembler;

import com.tteokguk.tteokguk.tteokguk.application.dto.response.CreateTteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TteokgukResponseAssembler {
    public static CreateTteokgukResponse toTteokgukResponse(Tteokguk tteokguk) {
        return CreateTteokgukResponse.builder()
                .tteokgukId(tteokguk.getId())
                .memberId(tteokguk.getMember().getId())
                .wish(tteokguk.getWish())
                .ingredients(tteokguk.getIngredients())
                .access(tteokguk.isAccess())
                .completion(tteokguk.isCompletion())
                .build();
    }
}
