package com.tallybackend.tally_backend.dto;

import lombok.Data;

@Data
public class LedgerEntryDto {
    private String ledgerName;
    private double amount; // +ve ya -ve frontend decide karega
}

