package com.tteokguk.tteokguk.support.presentation;

import com.tteokguk.tteokguk.global.dto.response.ApiPageResponse;
import com.tteokguk.tteokguk.global.security.annotation.AuthId;
import com.tteokguk.tteokguk.support.application.SupportService;
import com.tteokguk.tteokguk.support.application.dto.SupportRequest;
import com.tteokguk.tteokguk.support.application.dto.request.SupportPageableRequest;
import com.tteokguk.tteokguk.support.application.dto.response.ReceivedIngredientResponse;
import com.tteokguk.tteokguk.support.application.dto.response.SupportResponse;
import com.tteokguk.tteokguk.support.application.dto.response.SupportTteokgukResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/ingredient")
    public ResponseEntity<ApiPageResponse<ReceivedIngredientResponse>> getReceivedIngredientResponse(
            @AuthId Long id,
            @Valid SupportPageableRequest request
    ) {
        Page<ReceivedIngredientResponse> pagedResponse = supportService.getReceivedIngredientResponse(id, request);
        ApiPageResponse<ReceivedIngredientResponse> pagedApiResponse = ApiPageResponse.of(pagedResponse);
        return ResponseEntity.ok(pagedApiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<SupportTteokgukResponse>> getSupportResponse(
            @AuthId Long id,
            @Valid SupportPageableRequest request
    ) {
        Page<SupportTteokgukResponse> pageResponse = supportService.getSupportTteokgukResponse(id, request);
        ApiPageResponse<SupportTteokgukResponse> pagedApiResponse = ApiPageResponse.of(pageResponse);
        return ResponseEntity.ok(pagedApiResponse);
    }
}
