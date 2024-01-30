package com.tteokguk.tteokguk.support.application.dto.response;

import java.util.List;

public record SupportTteokgukResponse(
        Long tteokgukId,
        String receiverNickname,
        Boolean completion,
        List<?> ingredients
) {
}
