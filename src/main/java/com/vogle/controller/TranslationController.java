package com.vogle.controller;

import com.deepl.api.DeepLException;
import com.vogle.dto.TranslationRequest;
import com.vogle.dto.TranslationResponse;
import com.vogle.services.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/translation")
//@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public ResponseEntity<TranslationResponse> translate(
            @RequestBody TranslationRequest request) throws DeepLException, InterruptedException {

        TranslationResponse response = translationService.translate(request);
        return ResponseEntity.ok(response);
    }

//    @PostMapping
//    public String translate()
}
