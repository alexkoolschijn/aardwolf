/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport;

import java.io.InputStream;
import java.util.Collection;

public interface CollectionReader<T> {
    /**
     * Reads T data from a source and decodes to T objects
     * @param mimeType mime type of the supplied data. Can be used to determine if supplied data is valid for this reader.
     * @param fileName file name (if any) of the supplied data. Can be used to determine if supplied data is valid for this reader.
     * @param inputStream an {@link InputStream} containing the experiment data
     * @return a {@link Collection} of items of type T. This collection can be empty but is never {@code null}
     * @throws IllegalArgumentException if the data was corrupt or in an unsupported format for this reader
     */
    Collection<T> read(String mimeType, String fileName, InputStream inputStream);

    /**
     * @return a name or short description of the reader
     */
    String getName();
}
