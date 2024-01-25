package com.tteokguk.tteokguk.member.domain;

import com.tteokguk.tteokguk.item.domain.Item;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OAuthMember extends Member {

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type")
    private ProviderType providerType;

    @Column(name = "resource_id")
    private String resourceId;

    private OAuthMember(
            ProviderType providerType,
            String resourceId,
            String nickname,
            Ingredient primaryIngredient,
            List<Item> items,
            RoleType role
    ) {
        super(primaryIngredient, nickname, items, role);
        this.providerType = providerType;
        this.resourceId = resourceId;
    }

    public static OAuthMember of(ProviderType providerType, String resourceId, String nickname, RoleType role) {
        Ingredient primaryIngredient = Ingredient.random();

        OAuthMember member = new OAuthMember(
                providerType, resourceId, nickname, primaryIngredient, new ArrayList<>(), role
        );

        member.initializeItem(primaryIngredient);
        return member;
    }
}
