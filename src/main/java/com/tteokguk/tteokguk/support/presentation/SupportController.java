package com.tteokguk.tteokguk.support.presentation;

import com.tteokguk.tteokguk.global.security.annotation.AuthId;
import com.tteokguk.tteokguk.support.application.SupportService;
import com.tteokguk.tteokguk.support.application.dto.SupportRequest;
import com.tteokguk.tteokguk.support.application.dto.response.SupportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/support")
public class SupportController {

    private final SupportService supportService;

    @PostMapping
    public ResponseEntity<SupportResponse> doSupport(
            @AuthId Long id,
            @RequestBody @Validated SupportRequest request
    ) {
        SupportResponse response = supportService.doSupport(id, request);
        return ResponseEntity.ok(response);
    }
}
