package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.dto.VoucherRequest;
import com.tallybackend.tally_backend.service.VoucherXmlService;
import com.tallybackend.tally_backend.service.parser.VoucherParseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherController {

    @Autowired
    private VoucherXmlService xmlService;

    @Autowired
    private VoucherParseService voucherParseService;

    @PostMapping(
            consumes = "application/xml",
            produces = "application/json"
    )
    private List<String> getAllVoucher(@RequestBody String xml){
        return voucherParseService.extractVoucherNames(xml);
    }

    @PostMapping("/xml")
    public String generateVoucherXml(@RequestBody VoucherRequest request) {
        return xmlService.generateVoucherXml(request);
    }

}
