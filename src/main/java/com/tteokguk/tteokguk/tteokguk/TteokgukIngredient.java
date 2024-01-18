package com.tteokguk.tteokguk.tteokguk;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import com.tteokguk.tteokguk.global.auditing.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "t_tteokguk_ingredient")
public class TteokgukIngredient extends BaseEntity {

	@Id
	@Column(name = "tteokguk_ingredient_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@JoinColumn(name = "tteokguk_id")
	@ManyToOne(fetch = LAZY)
	private Tteokguk tteokguk;
}
