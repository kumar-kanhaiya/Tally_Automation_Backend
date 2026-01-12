package com.tallybackend.tally_backend.service;

import com.tallybackend.tally_backend.client.TallyClient;
import com.tallybackend.tally_backend.service.parser.LedgerParserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerService {

    private final TallyClient tallyClient;

    private final LedgerParserService ledgerParserService;

    public LedgerService(TallyClient tallyClient , LedgerParserService ledgerParserService){
        this.tallyClient = tallyClient;
        this.ledgerParserService = ledgerParserService;

    }

    public List<String> fetchLedgers(String companyName){
        String xml = tallyClient.fetchLedgersXml(companyName);
        return new ArrayList<>();
    }
}
