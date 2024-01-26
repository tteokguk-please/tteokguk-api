package com.tteokguk.tteokguk.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

	@Id
	@GeneratedValue
	@Column(name = "rt_id")
	private Long id;

	@Column(name = "member_id", nullable = false, unique = true)
	private Long memberId;

	@Column(name = "token", nullable = false, unique = true)
	private String token;

	@Builder
	public RefreshToken(Long memberId, String token) {
		this.memberId = memberId;
		this.token = token;
	}

	public void update(String token) {
		this.token = token;
	}
}
