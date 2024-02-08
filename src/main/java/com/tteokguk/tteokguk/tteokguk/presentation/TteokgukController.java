package com.tteokguk.tteokguk.tteokguk.presentation;

import com.tteokguk.tteokguk.global.dto.response.ApiPageResponse;
import com.tteokguk.tteokguk.global.security.annotation.AuthId;
import com.tteokguk.tteokguk.support.application.dto.request.PageableRequest;
import com.tteokguk.tteokguk.tteokguk.application.TteokgukService;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.IngredientRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.TteokgukResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @PostMapping("/completion/{tteokgukId}")
    public ResponseEntity<TteokgukResponse> doCompletion(
            @AuthId Long id,
            @PathVariable Long tteokgukId
    ) {
        TteokgukResponse response = tteokgukService.doCompletion(id, tteokgukId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{tteokgukId}")
    public ResponseEntity<TteokgukResponse> getTteokguk(
            @AuthId Long memberId, @PathVariable Long tteokgukId
    ) {
        TteokgukResponse response = tteokgukService.getTteokguk(memberId, tteokgukId);
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

    @GetMapping("/random")
    public ResponseEntity<TteokgukResponse> getRandomTteokguk() {
        TteokgukResponse response = tteokgukService.getRandomTteokguk();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/new")
    public ResponseEntity<ApiPageResponse<TteokgukResponse>> findNewTteokguks(
            @AuthId Long id,
            @Valid PageableRequest request
    ) {
        Page<TteokgukResponse> response = tteokgukService.findNewTteokguks(request);
        ApiPageResponse<TteokgukResponse> pagedApiResponse = ApiPageResponse.of(response);
        return ResponseEntity.ok(pagedApiResponse);
    }

    @GetMapping("/completion")
    public ResponseEntity<ApiPageResponse<TteokgukResponse>> findCompletionTteokguks(
            @AuthId Long id,
            @Valid PageableRequest request
    ) {
        Page<TteokgukResponse> response = tteokgukService.findCompletionTteokguks(request);
        ApiPageResponse<TteokgukResponse> pagedApiResponse = ApiPageResponse.of(response);
        return ResponseEntity.ok(pagedApiResponse);
    }
}
