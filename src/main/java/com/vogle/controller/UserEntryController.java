package com.vogle.controller;

import com.vogle.dto.UserEntryRequest;
import com.vogle.dto.UserEntryResponse;
import com.vogle.services.UserEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-entries")
public class UserEntryController {

    private final UserEntryService userEntryService;

    public UserEntryController(UserEntryService userEntryService) {
        this.userEntryService = userEntryService;
    }

    @PostMapping
    public ResponseEntity<UserEntryResponse> save(
            @RequestBody UserEntryRequest request) {

        return ResponseEntity.ok(userEntryService.save(request));
    }
}