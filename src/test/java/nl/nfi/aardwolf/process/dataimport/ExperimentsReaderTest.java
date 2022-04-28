/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.process.dataimport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.process.dataimport.argus.ArgusHelper;
import nl.nfi.aardwolf.processes.dataimport.CollectionReader;
import nl.nfi.aardwolf.processes.dataimport.ExperimentsReader;
import nl.nfi.aardwolf.processes.dataimport.UnsupportedFormatException;
import nl.nfi.aardwolf.processes.dataimport.argus.ArgusReader;

@SpringBootTest
public class ExperimentsReaderTest {

    private Collection<Experiment> read(final List<CollectionReader<Experiment>> readers) throws Exception {
        return ExperimentsReader.readExperiments(
                                                 readers,
                                                 "application/zip",
                                                 "emulator_make_photo.zip",
                                                 getClass().getResourceAsStream("/emulator_make_photo.zip"));
    }

    @Test
    public void testSingleSuccessfulReader() throws Exception {
        final List<CollectionReader<Experiment>> readers = List.of(new ArgusReader());
        ArgusHelper.testValidZipExperimentsOutput(read(readers));
    }

    @Test
    public void testIfOneReaderFailsAnotherPicksItUp() throws Exception {
        final List<CollectionReader<Experiment>> readers = Arrays.asList(new FailingDummyReader(), new ArgusReader());
        ArgusHelper.testValidZipExperimentsOutput(read(readers));
    }

    @Test
    public void testExceptionThrownWhenNoValidReadersAvailable() {
        final List<CollectionReader<Experiment>> readers = Arrays.asList(new FailingDummyReader(), new FailingDummyReader());
        final UnsupportedFormatException exception = assertThrows(UnsupportedFormatException.class, () -> read(readers));
        final List<Exception> exceptions = exception.getExceptions();
        assertEquals(exceptions.size(), 2);
        exceptions.forEach(e -> e.getClass().isInstance(IllegalArgumentException.class));
    }
}
