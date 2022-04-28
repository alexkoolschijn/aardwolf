/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport.argus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.domain.FileDelta;
import nl.nfi.aardwolf.domain.ModificationType;
import nl.nfi.aardwolf.processes.dataimport.CollectionReader;
import nl.nfi.aardwolf.processes.dataimport.argus.metadata.ArgusMetadata;
import nl.nfi.aardwolf.processes.dataimport.argus.metadata.FileStatus;

public class ArgusReader implements CollectionReader<Experiment> {
    private static final Logger LOG = LoggerFactory.getLogger(ArgusReader.class);

    private static final Pattern OS_VERSION_PATTERN = Pattern.compile(".* Initializing .* device: .* \\((.*)\\): device_id=\".+\"");
    private static final String OS_VERSION = "osVersion";

    // public for testing purposes
    public static final String ILLEGAL_ARGUMENT_METADATA = "Input Data does not contain Argus output (metadata.json is missing or unreadable)";
    public static final String ILLEGAL_ARGUMENT_LOGDATA = "Input Data does not contain Argus output (argus.log is missing or unreadable)";
    public static final String ILLEGAL_FILETYPE = "Input data is either an empty zip or not a zip at all";
    public static final String READER_NAME = "Argus (zip)";

    @Override
    public Collection<Experiment> read(final String mimeType, final String fileName, final InputStream inputStream) {
        Objects.requireNonNull(inputStream);

        final ZipInputStream zip = new ZipInputStream(inputStream);
        ZipEntry entry;
        ArgusMetadata metadata = null;
        Map<String, String> logdata = null;
        try {
            entry = zip.getNextEntry();
            if (entry == null) {
                throw new IllegalArgumentException(ILLEGAL_FILETYPE);
            }
            while ((metadata == null || logdata == null) && entry != null) {
                if (entry.getName().matches(".*/acquisition_info/metadata.json")) {
                    metadata = readMetadata(zip);
                }
                if (entry.getName().matches(".*/acquisition_info/argus.log")) {
                    logdata = readLog(zip);
                }
                entry = zip.getNextEntry();
            }
        }
        catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
        if (metadata == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_METADATA);
        }
        if (logdata == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_LOGDATA);
        }

        final Experiment exp = new Experiment();
        exp.setDateTimePerformed(
                                 LocalDateTime.parse(
                                                     metadata.acquisition.comparison.snapshotComparison.time.start.utc,
                                                     DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        exp.setOperatingSystem(logdata.get(OS_VERSION));
        exp.setDeviceDetails(toJson(metadata.device.system));
        exp.setMethodDetails(toJson(metadata.acquisition.experiment.method));
        exp.setDescription(metadata.acquisition.experiment.description);
        final List<FileDelta> deltas = new ArrayList<>();
        for (final FileStatus status : metadata.acquisition.comparison.snapshotComparison.files) {
            deltas.add(new FileDelta(ModificationType.fuzzyValueOf(status.status), status.type, status.file));
        }
        exp.setChanges(deltas);
        return Arrays.asList(exp);
    }

    private String toJson(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (final JsonProcessingException e) {
            LOG.error("Error writing object to JSON: {}", e.getMessage(), e);
            return "";
        }
    }

    private Map<String, String> readLog(final ZipInputStream zip) {
        final Map<String, String> attributes = new HashMap<>();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(zip));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                final Matcher osVersionMatcher = OS_VERSION_PATTERN.matcher(line);
                if (osVersionMatcher.matches()) {
                    attributes.put(OS_VERSION, osVersionMatcher.group(1));
                    continue;
                }
            }
        }
        catch (final IOException e) {
            LOG.error("Error reading from the argus log!", e);
        }
        return attributes;
    }

    private ArgusMetadata readMetadata(final ZipInputStream zip) throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper(new JsonFactory().disable(Feature.AUTO_CLOSE_SOURCE)).readValue(zip, ArgusMetadata.class);
    }

    @Override
    public String getName() {
        return READER_NAME;
    }
}
