package com.tallybackend.tally_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class VoucherRequest {
    private String companyName;
    private String voucherType; // Contra, Payment, Receipt
    private String date;        // yyyyMMdd
    private String narration;
    private List<LedgerEntryDto> entries;
}

