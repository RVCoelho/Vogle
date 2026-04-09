package com.vogle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserEntryResponse {

    private UUID id;
    private UUID entryId;
    private UUID learningLanguageId;
    private String notes;
    private LocalDateTime dateAdded;
}