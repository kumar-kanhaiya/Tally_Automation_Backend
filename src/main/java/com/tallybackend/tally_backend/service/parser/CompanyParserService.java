package com.tallybackend.tally_backend.service.parser;

import com.tallybackend.tally_backend.util.XmlSanitizer;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


// filtering company details
@Service
public class CompanyParserService {

    public List<String> extractCompanyNames(String xml) {
        List<String> companies = new ArrayList<>();

        try {
            String cleanXml = XmlSanitizer.sanitize(xml);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            // SECURITY HARDENING (DO NOT REMOVE)
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setExpandEntityReferences(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(
                    new InputSource(new StringReader(cleanXml))
            );

            NodeList companyNodes = doc.getElementsByTagName("COMPANY");

            for (int i = 0; i < companyNodes.getLength(); i++) {
                Element companyElement = (Element) companyNodes.item(i);

                String name = null;

                NodeList nameNodes = companyElement.getElementsByTagName("NAME");
                if (nameNodes.getLength() > 0) {
                    name = nameNodes.item(0).getTextContent();
                } else {
                    name = companyElement.getAttribute("NAME");
                }

                if (name != null && !name.isBlank()) {
                    companies.add(name.trim());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse company XML", e);
        }

        return companies;
    }


}

