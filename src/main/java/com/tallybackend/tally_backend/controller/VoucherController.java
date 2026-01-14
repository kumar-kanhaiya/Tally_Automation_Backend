package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.service.parser.VoucherParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

    @Autowired
    private VoucherParseService voucherParseService;

    @PostMapping(
            consumes = "application/xml",
            produces = "application/json"
    )
    private List<String> getAllVoucher(@RequestBody String xml){
        return voucherParseService.extractVoucherNames(xml);
    }

}
