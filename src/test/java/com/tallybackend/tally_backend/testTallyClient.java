package com.tallybackend.tally_backend;

import com.tallybackend.tally_backend.client.TallyClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class TallyClientTest {

    @Autowired
    private TallyClient tallyClient;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldFetchCompanyXml() {

        String mockXmlResponse =
                "<COMPANIES><COMPANY>ABC</COMPANY></COMPANIES>";

        mockServer.expect(requestTo("http://localhost:9000"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        mockXmlResponse,
                        MediaType.TEXT_XML
                ));

        String response = tallyClient.fetchCompanyXml();

        assertEquals(mockXmlResponse, response);
        mockServer.verify();
    }
}
