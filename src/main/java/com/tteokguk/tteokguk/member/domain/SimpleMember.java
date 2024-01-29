package com.tteokguk.tteokguk.member.domain;

import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tteokguk.tteokguk.item.domain.Item;
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
            unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    private SimpleMember(
            String email,
            String password,
            String nickname,
            Ingredient primaryIngredient,
            List<Item> items,
            RoleType role,
    Boolean acceptsMarketing) {
        super(primaryIngredient, nickname, items, role, acceptsMarketing);
        this.email = email;
        this.password = password;
    }

	public static SimpleMember of(
			String email,
			String password,
			String nickname,
			RoleType role,
			Boolean acceptsMarketing
	) {
		Ingredient primaryIngredient = Ingredient.random();

		SimpleMember member = new SimpleMember(
				email, password, nickname, primaryIngredient, new ArrayList<>(), role, acceptsMarketing
		);

        member.initializeItem(primaryIngredient);
        return member;
    }

	@Override
	public void delete() {
		super.delete();
		this.email = this.email + "::deleted::" + UUID.randomUUID().toString().substring(0, 8);
	}
}
