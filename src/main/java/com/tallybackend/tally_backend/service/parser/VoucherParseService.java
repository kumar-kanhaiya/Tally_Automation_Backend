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

@Service
public class VoucherParseService {

    public List<String> extractVoucherNames(String xml) {

        List<String> vouchers = new ArrayList<>();

        try {
            String cleanXml = XmlSanitizer.sanitize(xml);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            // Security (correct)
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setExpandEntityReferences(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(
                    new InputSource(new StringReader(cleanXml))
            );

            // IMPORTANT: uppercase
            NodeList voucherNodes = doc.getElementsByTagName("VOUCHER");

            for (int i = 0; i < voucherNodes.getLength(); i++) {

                Element voucherElement = (Element) voucherNodes.item(i);

                String voucherType = null;

                // OPTION 1: Read attribute
                if (voucherElement.hasAttribute("VCHTYPE")) {
                    voucherType = voucherElement.getAttribute("VCHTYPE");
                }

                // OPTION 2: Fallback to child tag
                if (voucherType == null || voucherType.isBlank()) {
                    NodeList typeNodes =
                            voucherElement.getElementsByTagName("VOUCHERTYPENAME");

                    if (typeNodes.getLength() > 0) {
                        voucherType = typeNodes.item(0).getTextContent();
                    }
                }

                if (voucherType != null && !voucherType.isBlank()) {
                    vouchers.add(voucherType.trim());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse voucher XML", e);
        }

        return vouchers;
    }
}

