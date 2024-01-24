package com.tteokguk.tteokguk.member.domain;

import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
		List<Inventory> inventories,
		RoleType role
	) {
		super(primaryIngredient, nickname, inventories, role);
		this.providerType = providerType;
		this.resourceId = resourceId;
	}

	public static OAuthMember of(ProviderType providerType, String resourceId, String nickname, RoleType role) {
		Ingredient primaryIngredient = Ingredient.random();

		OAuthMember member = new OAuthMember(
			providerType, resourceId, nickname, primaryIngredient, new ArrayList<>(), role
		);

		member.initializeInventory(primaryIngredient);
		return member;
	}
}
