package com.tallybackend.tally_backend.service;

import com.tallybackend.tally_backend.client.TallyClient;
import com.tallybackend.tally_backend.service.parser.CompanyParserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final TallyClient tallyClient;
    private final CompanyParserService parser;

    public CompanyService(TallyClient tallyClient,
                          CompanyParserService parser) {
        this.tallyClient = tallyClient;
        this.parser = parser;
    }

//    public List<String> fetchAndFilterCompanies() {
////        String rawXml = tallyClient.fetchCompanyXml();
//        System.out.println(rawXml);
//        return parser.extractCompanyNames(rawXml);
//    }
}

