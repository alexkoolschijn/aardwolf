/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.helpers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JsonHelper {

    private static final Logger LOG = LoggerFactory.getLogger(JsonHelper.class);

    public static String jsonGetProperty(final String property, final String json) {
        final JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = null;
        try {
            jParser = jfactory.createParser(json);
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                final String fieldname = jParser.getCurrentName();
                if (property.equals(fieldname)) {
                    jParser.nextToken();
                    return jParser.getText();
                }
            }
            jParser.close();
        }
        catch (final IOException e) {
            LOG.debug("Failed to get {} property from json {}", property, json, e);
        }
        return "";
    }
}
