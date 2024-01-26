package com.tteokguk.tteokguk.member.domain;

import com.tteokguk.tteokguk.item.domain.Item;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

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
}
