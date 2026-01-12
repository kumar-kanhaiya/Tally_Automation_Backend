package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.service.LedgerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ledgers")
public class LedgerController {

    private final LedgerService service;

    public LedgerController(LedgerService service){
        this.service = service;
    }

    @GetMapping
    public List<String> getLedgers(
            @RequestParam String companyName) {
        return service.fetchLedgers(companyName);
    }

}
