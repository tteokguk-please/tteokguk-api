package com.tteokguk.tteokguk.support.domain;

import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import jakarta.persistence.*;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supported_tteokguk_id")
    private Tteokguk supportedTteokguk;

    @Column(name = "access")
    private boolean access;

    @Column(name = "reward_ingredient")
    private Ingredient rewardIngredient;

    @Column(name = "reward_quantity")
    private int rewardQuantity;

    @Builder
    private Support(
            Member sender,
            Member receiver,
            Tteokguk supportedTteokguk,
            boolean access
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.supportedTteokguk = supportedTteokguk;
        this.access = access;
        this.rewardIngredient = Ingredient.TOFU; //todo change random
        this.rewardQuantity = 3; //todo change random
    }

    public Support of(
            Member sender,
            Member receiver,
            Tteokguk supportedTteokguk,
            boolean access
    ) {
        return Support.builder()
                .sender(sender)
                .receiver(receiver)
                .supportedTteokguk(supportedTteokguk)
                .access(access)
                .build();
    }
}
