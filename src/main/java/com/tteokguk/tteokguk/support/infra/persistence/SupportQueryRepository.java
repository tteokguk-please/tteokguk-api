package com.tteokguk.tteokguk.support.infra.persistence;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tteokguk.tteokguk.support.application.dto.response.ReceivedIngredientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tteokguk.tteokguk.support.domain.QSupport.support;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SupportQueryRepository {

    private final JPAQueryFactory query;

    public List<ReceivedIngredientResponse> getReceivedIngredientResponse(
            Long id,
            Pageable pageable
    ) {
        List<Long> supportIds = getReceiverSupportIds(id, pageable);

        return query
                .select(getReceivedIngredientResponseConstructorExpression())
                .from(support)
                .where(support.id.in(supportIds).and(support.supportedTteokguk.deleted.eq(false)))
                .orderBy(support.id.desc())
                .fetch();
    }


    public List<Long> getReceiverSupportIds(
            Long id,
            Pageable pageable
    ) {
        return query.selectDistinct(support.id)
                .from(support)
                .where(support.receiver.id.eq(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(support.id.desc())
                .fetch();
    }

    public List<Long> getSenderSupportIds(
            Long id,
            Pageable pageable
    ) {
        return query.select(support.id)
                .from(support)
                .where(support.sender.id.eq(id))
                .groupBy(support.supportedTteokguk.id) // to filter unique Tteokguk
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(support.id.desc())
                .fetch();
    }

    private ConstructorExpression<ReceivedIngredientResponse> getReceivedIngredientResponseConstructorExpression() {
        return Projections.constructor(
                ReceivedIngredientResponse.class,
                support.id,
                support.sender.id,
                support.sender.nickname,
                support.supportIngredient,
                support.message,
                support.access,
                support.supportedTteokguk.id
        );
    }
}
