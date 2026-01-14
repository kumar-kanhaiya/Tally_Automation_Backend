package com.tallybackend.tally_backend.service;

import com.tallybackend.tally_backend.dto.LedgerEntryDto;
import com.tallybackend.tally_backend.dto.VoucherRequest;
import org.springframework.stereotype.Service;

@Service
public class VoucherXmlService {

    public String generateVoucherXml(VoucherRequest request) {

        StringBuilder ledgerXml = new StringBuilder();

        for (LedgerEntryDto entry : request.getEntries()) {

            String isDeemedPositive =
                    entry.getAmount() < 0 ? "Yes" : "No";

            ledgerXml.append("""
                <ALLLEDGERENTRIES.LIST>
                    <LEDGERNAME>%s</LEDGERNAME>
                    <ISDEEMEDPOSITIVE>%s</ISDEEMEDPOSITIVE>
                    <AMOUNT>%.2f</AMOUNT>
                </ALLLEDGERENTRIES.LIST>
            """.formatted(
                    entry.getLedgerName(),
                    isDeemedPositive,
                    entry.getAmount()
            ));
        }

        return """
        <ENVELOPE>
            <HEADER>
                <TALLYREQUEST>Import Data</TALLYREQUEST>
            </HEADER>
            <BODY>
                <IMPORTDATA>
                    <REQUESTDESC>
                        <REPORTNAME>Vouchers</REPORTNAME>
                        <STATICVARIABLES>
                            <SVCURRENTCOMPANY>%s</SVCURRENTCOMPANY>
                        </STATICVARIABLES>
                    </REQUESTDESC>
                    <REQUESTDATA>
                        <TALLYMESSAGE xmlns:UDF="TallyUDF">
                            <VOUCHER VCHTYPE="%s" ACTION="Create">
                                <DATE>%s</DATE>
                                <VOUCHERTYPENAME>%s</VOUCHERTYPENAME>
                                <NARRATION>%s</NARRATION>
                                %s
                            </VOUCHER>
                        </TALLYMESSAGE>
                    </REQUESTDATA>
                </IMPORTDATA>
            </BODY>
        </ENVELOPE>
        """.formatted(
                request.getCompanyName(),
                request.getVoucherType(),
                request.getDate(),
                request.getVoucherType(),
                request.getNarration(),
                ledgerXml.toString()
        );
    }
}

