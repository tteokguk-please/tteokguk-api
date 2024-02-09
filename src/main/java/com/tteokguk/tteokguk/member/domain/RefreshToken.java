package com.tteokguk.tteokguk.member.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "t_refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

	@Id
	@GeneratedValue
	@Column(name = "rt_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "user_agent")
	private String userAgent;

	@Column(name = "rt_expired_datetime", nullable = false)
	private LocalDateTime expiredDateTime;

	public RefreshToken(Member member, String token, String userAgent, LocalDateTime expiredDateTime) {
		this.member = member;
		this.token = token;
		this.userAgent = userAgent;
		this.expiredDateTime = expiredDateTime;
	}

	public void update(String token, String userAgent, LocalDateTime expiredDateTime) {
		this.token = token;
		this.userAgent = userAgent;
		this.expiredDateTime = expiredDateTime;
	}
}
