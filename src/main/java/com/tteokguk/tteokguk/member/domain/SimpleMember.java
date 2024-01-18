package com.tteokguk.tteokguk.member.domain;

import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SimpleMember extends Member {

	@Column(name = "email",
		updatable = false,
		unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	private SimpleMember(
		String email,
		String password,
		String nickname,
		Ingredient ingredient,
		List<Inventory> inventory
	) {
		super(ingredient, nickname, inventory);
		this.email = email;
		this.password = password;
	}

	// TODO: 전용 재료를 회원 가입 때 정하는 로직을 구현해야 한다.
	// TODO: Password Encoder를 통해 비밀번호를 암호화해야 한다.
	public static SimpleMember join(String email, String password, String nickname) {
		return new SimpleMember(email, password, nickname, Ingredient.BEEF, new ArrayList<>());
	}
}
