package com.tteokguk.tteokguk.tteokguk.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

import com.tteokguk.tteokguk.global.auditing.BaseEntity;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
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
@SQLRestriction("deleted = false")
@Table(name = "t_tteokguk")
@NoArgsConstructor(access = PROTECTED)
public class Tteokguk extends BaseEntity {

	@Id
	@Column(name = "tteokguk_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "wish", columnDefinition = "varchar(120)")
	private String wish;

	@Column(name = "deleted")
	@ColumnDefault("false")
	private boolean deleted;

	@Column(name = "access")
	private boolean access;

	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = LAZY)
	private Member member;

	@Column(name = "ingredient")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Ingredient.class)
	@CollectionTable(
		name = "t_tteokguk_ingredients",
		joinColumns = @JoinColumn(name = "tteokguk_id")
	)
	private List<Ingredient> tteokgukIngredients;

	@Builder(access = PROTECTED)
	private Tteokguk(
		String wish,
		List<Ingredient> tteokgukIngredients,
		Member member,
		boolean access
	) {
		this.wish = wish;
		this.tteokgukIngredients = tteokgukIngredients;
		this.member = member;
		this.access = access;
		this.deleted = false;
	}

	public static Tteokguk of(
		String wish,
		List<Ingredient> ingredients,
		Member member,
		boolean access
	) {
		return Tteokguk.builder()
			.wish(wish)
			.tteokgukIngredients(ingredients)
			.access(access)
			.member(member)
			.build();
	}

	public void delete() {
		this.deleted = true;
	}
}
