package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.client.TallyClient;
import com.tallybackend.tally_backend.service.CompanyService;
import com.tallybackend.tally_backend.service.parser.CompanyParserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyParserService parserService;

    public CompanyController(CompanyParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping(
            consumes = "application/xml",
            produces = "application/json"
    )
    public List<String> getCompanies(@RequestBody String xml) {
        return parserService.extractCompanyNames(xml);
    }
}


