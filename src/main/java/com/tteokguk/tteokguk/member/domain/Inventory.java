package com.tteokguk.tteokguk.member.domain;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = LAZY)
	private Member member;
}
