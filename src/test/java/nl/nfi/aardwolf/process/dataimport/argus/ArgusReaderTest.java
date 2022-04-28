/**
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.process.dataimport.argus;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.processes.dataimport.argus.ArgusReader;


@SpringBootTest
public class ArgusReaderTest {

    @Test
    public void testRead() throws Exception {
        final Collection<Experiment> experiments = new ArgusReader().read("application/zip", "emulator_make_photo.zip", getClass().getResourceAsStream("/emulator_make_photo.zip"));
        ArgusHelper.testValidZipExperimentsOutput(experiments);
    }

    @Test
    public void testInputFileDoesNotExist() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new ArgusReader().read("application/zip", "emulator_make_photo.zip", getClass().getResourceAsStream("/doesnotexist.zip"));
        });        
    }

    @Test
    public void testInputFileNotAZip() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ArgusReader().read("application/zip", "not-a-zip.zip", getClass().getResourceAsStream("/not-a-zip.zip"));
        });
        assertThat(thrown.getMessage(), equalTo(ArgusReader.ILLEGAL_FILETYPE));
    }

    @Test
    public void testInputFileIncomplete() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ArgusReader().read("application/zip", "emulator_make_photo-incomplete.zip", getClass().getResourceAsStream("/emulator_make_photo-incomplete.zip"));
        });
        assertThat(thrown.getMessage(), equalTo(ArgusReader.ILLEGAL_ARGUMENT_METADATA));
    }

    @Test
    public void testGetName() {
        assertThat("get name", new ArgusReader().getName(), equalTo(ArgusReader.READER_NAME));
    }
}
