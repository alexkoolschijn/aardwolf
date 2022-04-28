/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport;

import static java.util.stream.Collectors.joining;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.processes.dataimport.argus.ArgusReader;

public abstract class ExperimentsReader {

    private static final List<CollectionReader<Experiment>> READERS = Arrays.asList(new ArgusReader());
    private static final String READER_FORMATS = READERS.stream().map(r -> r.getName()).collect(joining(","));

    /**
     * Reads Experiments from data stream using a custom set of readers
     * If one reader fails, the next one attempts to read the input stream.
     * If none of the readers succeeds, an UnsupportedFormatException is thrown.
     *
     * @param readers a {@link List} of {@link ExperimentsReader}s
     * @param mimeType the MIME type of the supplied data
     * @param fileName the name of the supplied file
     * @param inputStream an input stream containing the experiment data
     * @return A collection of Experiment objects, never null.
     * @throws UnsupportedFormatException if any problem occurs reading data from the input stream
     */
    public static Collection<Experiment> readExperiments(
                                                         final List<CollectionReader<Experiment>> readers,
                                                         final String mimeType,
                                                         final String fileName,
                                                         final InputStream inputStream) throws Exception {

        final List<Exception> exceptions = new ArrayList<Exception>();
        final Collection<Experiment> experiments;
        for (final CollectionReader<Experiment> reader : readers) {
            try {
                inputStream.mark(0); // mark beginning of input stream
                return reader.read(mimeType, fileName, inputStream);
            }
            catch (final IllegalArgumentException e) {
                exceptions.add(e);
                inputStream.reset(); // reset input stream
            }
        }
        throw new UnsupportedFormatException(READER_FORMATS, exceptions);
    }

    /**
     * Reads Experiments from data stream using the default (locally specified) set of Readers
     *
     * @param mimeType the MIME type of the supplied data
     * @param fileName the name of the supplied file
     * @param inputStream an input stream containing the experiment data
     * @return A collection of Experiment objects, never null.
     * @throws UnsupportedFormatException if any problem occurs reading data from the input stream
     */
    public static Collection<Experiment> readExperiments(
                                                         final String mimeType,
                                                         final String fileName,
                                                         final InputStream inputStream) throws Exception {
        return readExperiments(READERS, mimeType, fileName, inputStream);
    }
}
