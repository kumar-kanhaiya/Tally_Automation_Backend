package com.tallybackend.tally_backend.util;

import java.util.regex.Pattern;

public class XmlSanitizer {

    private static final String INVALID_XML_CHARS =
            "[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]";

    public static String sanitize(String xml) {
        if (xml == null) return null;

        // Remove raw control characters
        xml = xml.replaceAll(INVALID_XML_CHARS, "");

        // Remove invalid numeric character references like &#4;
        xml = xml.replaceAll("&#(0?[0-9]|1[0-9]|2[0-9]|3[01]);", "");

        return xml;
    }
}

