package com.tteokguk.tteokguk.member.domain;

import java.time.LocalDateTime;

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

	@Column(name = "rt_expired_datetime", nullable = false)
	private LocalDateTime expiredDateTime;


	@Builder
	public RefreshToken(Long memberId, String token, LocalDateTime expiredDateTime) {
		this.memberId = memberId;
		this.token = token;
		this.expiredDateTime = expiredDateTime;
	}

	public void update(String token, LocalDateTime expiredDateTime) {
		this.token = token;
		this.expiredDateTime = expiredDateTime;
	}
}
