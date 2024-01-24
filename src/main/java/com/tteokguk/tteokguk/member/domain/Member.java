package com.tteokguk.tteokguk.member.domain;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.hibernate.annotations.OnDeleteAction.*;

import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.OnDelete;

import com.tteokguk.tteokguk.global.auditing.BaseEntity;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "t_member")
@NoArgsConstructor(access = PROTECTED)
@Inheritance
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(STRING)
	@Column(name = "primary_ingredient")
	private Ingredient primaryIngredient;

	@Column(name = "nickname")
	private String nickname;

	@OneToMany(
		mappedBy = "member",
		cascade = PERSIST,
		orphanRemoval = true)
	@OnDelete(action = CASCADE)
	private List<Inventory> inventories;

	@Enumerated
	@Column(name = "role")
	private RoleType role;

	protected Member(
		Ingredient primaryIngredient,
		String nickname,
		List<Inventory> inventories,
		RoleType role
	) {
		this.primaryIngredient = primaryIngredient;
		this.nickname = nickname;
		this.inventories = inventories;
		this.role = role;
	}

	// Initialize Inventory
	protected void initializeInventory(Ingredient primaryIngredient) {
		final int INF = 1_000_000_000;

		List<Inventory> initInventories = Arrays.stream(Ingredient.values())
			.filter(this::isNotPrimaryIngredient)
			.map(ingredient -> Inventory.create(ingredient, 0, this))
			.toList();

		Inventory primaryIngredientInventory = Inventory.create(primaryIngredient, INF, this);

		inventories.addAll(initInventories);
		inventories.add(primaryIngredientInventory);
	}

	private boolean isNotPrimaryIngredient(Object ingredient) {
		return !primaryIngredient.equals(ingredient);
	}
}
