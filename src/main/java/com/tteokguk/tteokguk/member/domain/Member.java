package com.tteokguk.tteokguk.member.domain;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.hibernate.annotations.OnDeleteAction.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.OnDelete;

import com.tteokguk.tteokguk.global.auditing.BaseEntity;
import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.exception.MemberError;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;

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

	@OneToMany(
		mappedBy = "member",
		cascade = PERSIST,
		orphanRemoval = true)
	@OnDelete(action = CASCADE)
	private final List<Tteokguk> tteokguks = new ArrayList<>();

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
	private List<Item> items = new ArrayList<>();

	@Enumerated(STRING)
	@Column(name = "role")
	private RoleType role;

	@Column(name = "accepts_marketing")
	private Boolean acceptsMarketing;

	protected Member(
		Ingredient primaryIngredient,
		String nickname,
		List<Item> items,
		Boolean acceptsMarketing
	) {
		this(primaryIngredient, nickname, items, RoleType.ROLE_USER, acceptsMarketing);
	}

	protected Member(
		Ingredient primaryIngredient,
		String nickname,
		List<Item> items,
		RoleType role,
		Boolean acceptsMarketing
	) {
		this.primaryIngredient = primaryIngredient;
		this.nickname = nickname;
		this.items = items;
		this.role = role;
		this.acceptsMarketing = acceptsMarketing;
	}

	// Initialize Item
	protected void initializeItem(Ingredient primaryIngredient) {
		final int INF = 1_000_000_000;

		List<Item> items = Arrays.stream(Ingredient.values())
			.filter(this::isNotPrimaryIngredient)
			.map(ingredient -> Item.of(ingredient, 0, this))
			.toList();

		Item primaryIngredientItem = Item.of(primaryIngredient, INF, this);

		this.items.addAll(items);
		this.items.add(primaryIngredientItem);
	}

	private boolean isNotPrimaryIngredient(Object ingredient) {
		return !primaryIngredient.equals(ingredient);
	}

	public void addTteokguk(Tteokguk tteokguk) {
		this.tteokguks.add(tteokguk);
	}

	public void initialize(String nickname, Boolean acceptsMarketing) {
		this.nickname = nickname;
		this.acceptsMarketing = acceptsMarketing;
		this.role = RoleType.ROLE_USER;
	}
}
