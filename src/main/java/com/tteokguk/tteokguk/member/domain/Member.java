package com.tteokguk.tteokguk.member.domain;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.hibernate.annotations.OnDeleteAction.*;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.springframework.util.ObjectUtils;

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
	private List<Inventory> inventory;

	protected Member(Ingredient primaryIngredient, String nickname, List<Inventory> inventory) {
		this.primaryIngredient = primaryIngredient;
		this.nickname = nickname;
		this.inventory = inventory;
	}

	public void store(Ingredient ingredient, int quantity) {
		List<Inventory> filteredInventory = getInventory().stream()
			.filter(i -> ObjectUtils.nullSafeEquals(i.getIngredient(), ingredient))
			.toList();

		if (filteredInventory.isEmpty()) {
			getInventory().add(new Inventory(ingredient, quantity, this));
		} else {
			// TODO: inventory에 데이터가 있는 경우 해당 데이터에서 stockQuantity를 늘려줘야 한다. (Atomic)
		}
	}

	protected void initInventory(Ingredient primaryIngredient) {
		final int INF = 1_000_000_000;
		store(primaryIngredient, INF);
	}
}
