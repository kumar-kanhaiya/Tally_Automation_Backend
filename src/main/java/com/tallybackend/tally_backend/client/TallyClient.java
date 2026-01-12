package com.tallybackend.tally_backend.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class TallyClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String tallyUrl = "http://localhost:9000";

    public String fetchCompanyXml(String xml){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        HttpEntity<String> request = new HttpEntity<>(xml,headers);

        return restTemplate.postForObject(
                tallyUrl,
                request,
                String.class
        );


    }

    public String fetchLedgersXml(String companyName) {

        String ledgerXml = """
                <ENVELOPE>
                  <HEADER>
                    <VERSION>1</VERSION>
                    <TALLYREQUEST>Export</TALLYREQUEST>
                    <TYPE>Collection</TYPE>
                    <ID>Ledger</ID>
                  </HEADER>
                  <BODY>
                    <DESC>
                      <STATICVARIABLES>
                        <SVCURRENTCOMPANY>%s</SVCURRENTCOMPANY>
                        <SVEXPORTFORMAT>$$SysName:XML</SVEXPORTFORMAT>
                      </STATICVARIABLES>
                    </DESC>
                  </BODY>
                </ENVELOPE>
                """.formatted(companyName);

        HttpHeaders headers =  new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        HttpEntity<String> request = new HttpEntity<>(ledgerXml , headers);

        return restTemplate.postForObject(
                tallyUrl,
                request,
                String.class
        );
    }


//    private static final String COMPANY_XML = """
//        <ENVELOPE>
//          <HEADER>
//            <VERSION>1</VERSION>
//            <TALLYREQUEST>Export</TALLYREQUEST>
//            <TYPE>Collection</TYPE>
//            <ID>List of Companies</ID>
//          </HEADER>
//          <BODY>
//            <DESC>
//              <STATICVARIABLES>
//                <SVEXPORTFORMAT>$$SysName:XML</SVEXPORTFORMAT>
//              </STATICVARIABLES>
//            </DESC>
//          </BODY>
//        </ENVELOPE>
//        """;


}



