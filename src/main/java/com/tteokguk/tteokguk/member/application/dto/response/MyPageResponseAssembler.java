package com.tteokguk.tteokguk.member.application.dto.response;

import com.tteokguk.tteokguk.member.domain.Item;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MyPageResponseAssembler {

    //== Entity List to Response List ==//
    public static MyPageResponse transferToMyPageResponse(Member member) {
        List<TteokgukResponse> tteokgukResponses = transferToTteokgukResponses(member.getTteokguks());
        List<ItemResponse> itemResponses = transferToItemResponses(member.getItems());

        return MyPageResponse.builder()
                .id(member.getId())
                .primaryIngredient(member.getPrimaryIngredient())
                .nickname(member.getNickname())
                .tteokguks(tteokgukResponses)
                .items(itemResponses)
                .build();
    }

    //== Tteokguk Response ==//
    private static List<TteokgukResponse> transferToTteokgukResponses(List<Tteokguk> tteokguks) {
        return tteokguks.stream()
                .map(MyPageResponseAssembler::toTteokgukResponse)
                .toList();
    }

    private static TteokgukResponse toTteokgukResponse(Tteokguk tteokguk) {
        return TteokgukResponse.builder()
                .tteokgukId(tteokguk.getId())
                .wish(tteokguk.getWish())
                .access(tteokguk.isAccess())
                .tteokgukIngredients(tteokguk.getTteokgukIngredients())
                .build();
    }

    //== Item Response ==//
    private static List<ItemResponse> transferToItemResponses(List<Item> items) {
        return items.stream()
                .map(MyPageResponseAssembler::toItemResponses)
                .toList();
    }

    private static ItemResponse toItemResponses(Item item) {
        return ItemResponse.builder()
                .ingredient(item.getIngredient())
                .stockQuantity(item.getStockQuantity())
                .build();
    }
}
