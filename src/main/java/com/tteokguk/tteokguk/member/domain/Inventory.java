package com.tteokguk.tteokguk.member.domain;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "t_inventory")
@NoArgsConstructor(access = PROTECTED)
public class Inventory {

	@Id
	@Column(name = "inventory_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(STRING)
	@Column(name = "ingredient")
	private Ingredient ingredient;

	@Column(name = "stock_quantity")
	private int stockQuantity;

	@JsonIgnore
	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = LAZY)
	private Member member;

	@Builder(access = PRIVATE)
	private Inventory(
		Ingredient ingredient,
		int stockQuantity,
		Member member
	) {
		this.ingredient = ingredient;
		this.stockQuantity = stockQuantity;
		this.member = member;
	}

	public static Inventory create(
		Ingredient ingredient,
		int stockQuantity,
		Member member
	) {
		return Inventory.builder()
			.ingredient(ingredient)
			.stockQuantity(stockQuantity)
			.member(member)
			.build();
	}
}
