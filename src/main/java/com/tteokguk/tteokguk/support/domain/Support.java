package com.tteokguk.tteokguk.support.domain;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

import static com.tteokguk.tteokguk.support.exception.SupportError.CANT_SEND_TO_OWN_SELF;
import static jakarta.persistence.EnumType.STRING;
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

    @Enumerated(STRING)
    @Column(name = "support_ingredient")
    private Ingredient supportIngredient;

    @Column(name = "access")
    private boolean access;

    @Column(name = "message")
    private String message;

    @Enumerated(STRING)
    @Column(name = "reward_ingredient")
    private Ingredient rewardIngredient;

    @Column(name = "reward_quantity")
    private int rewardQuantity;

    @Builder
    private Support(
            Member sender,
            Member receiver,
            Tteokguk supportedTteokguk,
            Ingredient supportIngredient,
            boolean access,
            String message
    ) {
        sender.validateHasSufficientIngredients(List.of(supportIngredient));
        supportedTteokguk.validateAlreadyUsedIngredients(List.of(supportIngredient));
        supportedTteokguk.validateHasAppropriateIngredients(List.of(supportIngredient));

        if (sender.equals(receiver)) {
            throw BusinessException.of(CANT_SEND_TO_OWN_SELF);
        }

        Random random = new Random();

        this.sender = sender;
        this.receiver = receiver;
        this.supportedTteokguk = supportedTteokguk;
        this.supportIngredient = supportIngredient;
        this.access = access;
        this.message = message;
        do {
            this.rewardIngredient = Ingredient.random();
        } while (sender.getPrimaryIngredient() == this.rewardIngredient);
        this.rewardQuantity = random.nextInt(1, 7);

        sender.sendIngredient(receiver, supportIngredient);
        sender.giftToSender(rewardIngredient, rewardQuantity);
        supportedTteokguk.useIngredients(List.of(supportIngredient));
    }

    public static Support of(
            Member sender,
            Member receiver,
            Tteokguk supportedTteokguk,
            Ingredient supportIngredient,
            boolean access,
            String message
    ) {
        return Support.builder()
                .sender(sender)
                .receiver(receiver)
                .supportedTteokguk(supportedTteokguk)
                .supportIngredient(supportIngredient)
                .access(access)
                .message(message)
                .build();
    }
}
