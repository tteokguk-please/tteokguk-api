package com.tteokguk.tteokguk.tteokguk.presentation;

import com.tteokguk.tteokguk.global.security.annotation.AuthId;
import com.tteokguk.tteokguk.tteokguk.application.TteokgukService;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.IngredientRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.TteokgukResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tteokguk")
public class TteokgukController {

    private final TteokgukService tteokgukService;

    @PostMapping
    public ResponseEntity<TteokgukResponse> createTteokguk(
            @AuthId Long id,
            @RequestBody @Validated CreateTteokgukRequest request
    ) {
        TteokgukResponse response = tteokgukService.createTteokguk(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/use")
    public ResponseEntity<TteokgukResponse> useIngredient(
            @AuthId Long id,
            @RequestBody @Validated IngredientRequest request
    ) {
        TteokgukResponse response = tteokgukService.useIngredients(id, request);
        return ResponseEntity.ok(response);
    }
}
