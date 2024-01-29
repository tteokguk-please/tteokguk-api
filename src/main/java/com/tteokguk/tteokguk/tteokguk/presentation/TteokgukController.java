package com.tteokguk.tteokguk.tteokguk.presentation;

import com.tteokguk.tteokguk.global.security.annotation.AuthId;
import com.tteokguk.tteokguk.tteokguk.application.TteokgukService;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.IngredientRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.TteokgukResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{tteokgukId}")
    public ResponseEntity<Void> deleteTteokguk(
            @AuthId Long id,
            @PathVariable Long tteokgukId
    ) {
        tteokgukService.deleteTteokguk(id, tteokgukId);
        return ResponseEntity.ok().build();
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
