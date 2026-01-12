package com.tallybackend.tally_backend.service.parser;
import com.tallybackend.tally_backend.util.XmlSanitizer;
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
        try {
            String cleanXml = XmlSanitizer.sanitize(xml);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            // SECURITY
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setExpandEntityReferences(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(
                    new InputSource(new StringReader(cleanXml))
            );

            NodeList ledgerNodes = doc.getElementsByTagName("LEDGER");

            List<String> ledgers = new ArrayList<>();

            for (int i = 0; i < ledgerNodes.getLength(); i++) {
                Element ledger = (Element) ledgerNodes.item(i);

                NodeList nameNodes = ledger.getElementsByTagName("NAME");
                if (nameNodes.getLength() > 0) {
                    String name = nameNodes.item(0).getTextContent();
                    if (name != null && !name.isBlank()) {
                        ledgers.add(name.trim());
                    }
                }
            }

            return ledgers;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse ledger XML", e);
        }
    }
}

