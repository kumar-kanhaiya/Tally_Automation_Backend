package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.service.LedgerService;
import com.tallybackend.tally_backend.service.parser.LedgerParserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ledgers")
public class LedgerController {

    private final LedgerService service;
    private final LedgerParserService ledgerService;

    public LedgerController(LedgerService service , LedgerParserService ledgerService){
        this.service = service;
        this.ledgerService = ledgerService;
    }

    @GetMapping
    public List<String> getLedgers(
            @RequestParam String companyName) {
        return service.fetchLedgers(companyName);
    }

    @PostMapping(
            consumes = "application/xml",
            produces = "application/json"
    )
    public List<String> getAllLedgers(@RequestBody String xml){
        return ledgerService.extractLedgerNames(xml);
    }

}
