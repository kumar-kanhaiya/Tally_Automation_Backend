package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.client.TallyClient;
import com.tallybackend.tally_backend.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService service;

    private final TallyClient tallyClient;

    public CompanyController(CompanyService service , TallyClient tallyClient) {
        this.service = service;
        this.tallyClient = tallyClient;
    }

    @GetMapping
    public List<String> getCompanies() {
        return service.fetchAndFilterCompanies();
    }

    @GetMapping("/xml")
    public String getXmlInfo(){
        return tallyClient.fetchCompanyXml();
    }
}

