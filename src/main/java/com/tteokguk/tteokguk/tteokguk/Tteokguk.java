package com.tteokguk.tteokguk.tteokguk;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.hibernate.annotations.OnDeleteAction.*;

import java.util.List;

import org.hibernate.annotations.OnDelete;

import com.tteokguk.tteokguk.global.auditing.BaseEntity;
import com.tteokguk.tteokguk.global.constants.BooleanStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "t_tteokguk")
@NoArgsConstructor(access = PROTECTED)
public class Tteokguk extends BaseEntity {

	@Id
	@Column(name = "tteokguk_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "wish", columnDefinition = "varchar(120)")
	private String wish;

	@Enumerated(STRING)
	@Column(name = "public_status")
	private BooleanStatus publicStatus;

	@OneToMany(
		mappedBy = "tteokguk",
		cascade = PERSIST,
		orphanRemoval = true)
	@OnDelete(action = CASCADE)
	private List<TteokgukIngredient> tteokgukIngredients;
}
