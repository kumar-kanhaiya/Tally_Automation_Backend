package com.tallybackend.tally_backend;

import com.tallybackend.tally_backend.service.VoucherXmlService;
import com.tallybackend.tally_backend.dto.VoucherRequest;
import com.tallybackend.tally_backend.dto.LedgerEntryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VoucherTesting {

    @Autowired
    private VoucherXmlService voucherXmlService;

    @Test
    public void xmlServiceTest() {

        // ---------- GIVEN ----------
        LedgerEntryDto bank = new LedgerEntryDto();
        bank.setLedgerName("CANARA BANK");
        bank.setAmount(15000);

        LedgerEntryDto cash = new LedgerEntryDto();
        cash.setLedgerName("Cash");
        cash.setAmount(-15000);

        VoucherRequest request = new VoucherRequest();
        request.setCompanyName("ABC Pvt Ltd");
        request.setVoucherType("Contra");
        request.setDate("20250601");
        request.setNarration("Cash deposited into bank");
        request.setEntries(List.of(bank, cash));

        // ---------- WHEN ----------
        String xml = voucherXmlService.generateVoucherXml(request);

        // ---------- THEN ----------
        assertNotNull(xml, "Generated XML should not be null");
        assertFalse(xml.isBlank(), "Generated XML should not be blank");

        // Voucher level checks
        assertTrue(xml.contains("<VOUCHERTYPENAME>Contra</VOUCHERTYPENAME>"));
        assertTrue(xml.contains("<DATE>20250601</DATE>"));
        assertTrue(xml.contains("<SVCURRENTCOMPANY>ABC Pvt Ltd</SVCURRENTCOMPANY>"));

        // Ledger checks
        assertTrue(xml.contains("<LEDGERNAME>CANARA BANK</LEDGERNAME>"));
        assertTrue(xml.contains("<AMOUNT>15000.00</AMOUNT>"));
        assertTrue(xml.contains("<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"));

        assertTrue(xml.contains("<LEDGERNAME>Cash</LEDGERNAME>"));
        assertTrue(xml.contains("<AMOUNT>-15000.00</AMOUNT>"));
        assertTrue(xml.contains("<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"));
        System.out.println(xml);
    }
}
