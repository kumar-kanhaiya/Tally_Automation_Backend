package com.tallybackend.tally_backend;

import com.tallybackend.tally_backend.client.TallyClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testTallyClient {

    @Autowired
    private TallyClient client;

    @Test
    public void testClientFase(){
        String output = client.
    }
}
