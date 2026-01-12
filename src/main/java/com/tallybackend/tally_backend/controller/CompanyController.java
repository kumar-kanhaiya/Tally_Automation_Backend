package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.client.TallyClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tally")
public class CompanyController {

    private final TallyClient tallyClient;

    public CompanyController(TallyClient tallyClient){
        this.tallyClient = tallyClient;

    }

    @GetMapping("/companies")
    public String getCompanies() {
        return tallyClient.fetchCompanyXml();
    }
}
