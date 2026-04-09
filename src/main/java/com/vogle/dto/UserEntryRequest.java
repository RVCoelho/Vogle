package com.vogle.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserEntryRequest {

    private UUID userId;
    private UUID entryId;
    private UUID learningLanguageId;
    private String notes;
}