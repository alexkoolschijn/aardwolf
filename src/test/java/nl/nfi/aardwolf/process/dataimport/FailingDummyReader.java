/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.process.dataimport;

import java.io.InputStream;
import java.util.Collection;

import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.processes.dataimport.CollectionReader;


public class FailingDummyReader implements CollectionReader<Experiment> {

    @Override
    public Collection<Experiment> read(final String mimeType, final String fileName, final InputStream inputStream) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getName() {
        return "Failing dummy reader";
    }
}
