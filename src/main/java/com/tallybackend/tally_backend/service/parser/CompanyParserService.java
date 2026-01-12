package com.tallybackend.tally_backend.service.parser;

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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(
                    new InputSource(new StringReader(xml))
            );

            NodeList companyNodes = doc.getElementsByTagName("COMPANY");

            for (int i = 0; i < companyNodes.getLength(); i++) {
                Element companyElement = (Element) companyNodes.item(i);

                // OPTION 1: Read NAME attribute
                String name = companyElement.getAttribute("NAME");

                // Fallback if attribute is missing
                if (name == null || name.isBlank()) {
                    NodeList nameNodes = companyElement.getElementsByTagName("NAME");
                    if (nameNodes.getLength() > 0) {
                        name = nameNodes.item(0).getTextContent();
                    }
                }

                if (name != null) {
                    name = name.trim();
//                    companies.add(name);

                    // FILTERING LOGIC
                    if (!name.equalsIgnoreCase("")) {
                        companies.add(name);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse company XML", e);
        }

        return companies;
    }
}

