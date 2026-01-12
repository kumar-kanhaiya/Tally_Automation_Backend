package com.tallybackend.tally_backend.service.parser;

import com.tallybackend.tally_backend.util.XmlSanitizer;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupParserService {

    public List<String> extractGroupNames(String xml) {
        List<String> groups = new ArrayList<>();

        try {
            // 1️⃣ Sanitize XML
            String cleanXml = XmlSanitizer.sanitize(xml);

            // 2️⃣ Secure DOM Factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            // SECURITY (IMPORTANT)
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setExpandEntityReferences(false);

            // 3️⃣ Parse XML
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(
                    new InputSource(new StringReader(cleanXml))
            );

            // 4️⃣ Fetch all GROUP nodes
            NodeList groupNodes = doc.getElementsByTagName("GROUP");

            for (int i = 0; i < groupNodes.getLength(); i++) {
                Element groupElement = (Element) groupNodes.item(i);

                String groupName = null;

                // Prefer NAME attribute (Tally standard)
                if (groupElement.hasAttribute("NAME")) {
                    groupName = groupElement.getAttribute("NAME");
                }

                // Fallback: NAME tag inside LANGUAGENAME
                if (groupName == null || groupName.isBlank()) {
                    NodeList nameNodes = groupElement.getElementsByTagName("NAME");
                    if (nameNodes.getLength() > 0) {
                        groupName = nameNodes.item(0).getTextContent();
                    }
                }

                if (groupName != null && !groupName.isBlank()) {
                    groups.add(groupName.trim());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "[ERROR]~[ Failed to parse Group XML ]",
                    e
            );
        }

        return groups;
    }
}
