package com.tteokguk.tteokguk.support.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_support")
@NoArgsConstructor(access = PROTECTED)
public class Support {

    @Id
    @Column(name = "support_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
