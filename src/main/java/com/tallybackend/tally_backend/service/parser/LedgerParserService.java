package com.tallybackend.tally_backend.service.parser;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerParserService {

        public List<String> extractLedgerNames(String xml) {
            List<String> ledgers = new ArrayList<>();

            try{
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(false);

                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(
                        new InputSource(new StringReader(xml))
                );

                NodeList ledgersList = doc.getElementsByTagName("LEDGER");

                for (int i = 0; i < ledgersList.getLength(); i++) {
                    Element ledgerElement = (Element) ledgersList.item(i);

                    String name = ledgerElement.getAttribute("NAME");

                    if(name == null || name.isBlank()){
                        NodeList nameNodes =
                                ledgerElement.getElementsByTagName("NAME");

                        if(nameNodes.getLength() > 0){
                            name = nameNodes.item(0).getTextContent();
                        }
                    }

                    if(name != null){
                        name = name.trim();

                        if (!name.equalsIgnoreCase("Profit & Loss")
                                && !name.equalsIgnoreCase("Balance Sheet")) {
                            ledgers.add(name);
                        }
                    }
                }


            } catch (Exception e) {
                throw new RuntimeException("Failed to parse company XML", e);
            }

            return ledgers;
        }
}
