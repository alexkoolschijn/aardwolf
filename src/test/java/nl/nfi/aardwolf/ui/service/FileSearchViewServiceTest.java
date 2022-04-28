/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import static nl.nfi.aardwolf.helper.DummyData.APP_1;
import static nl.nfi.aardwolf.helper.DummyData.APP_2;
import static nl.nfi.aardwolf.helper.DummyData.CREATED_FILE;
import static nl.nfi.aardwolf.helper.DummyData.DELETED_FILE;
import static nl.nfi.aardwolf.helper.DummyData.EXPERIMENT_1;
import static nl.nfi.aardwolf.helper.DummyData.EXPERIMENT_2;
import static nl.nfi.aardwolf.helper.DummyData.MODIFIED_FILE;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.domain.FileDelta;
import nl.nfi.aardwolf.domain.ModificationType;
import nl.nfi.aardwolf.domain.griddata.ExperimentToApp;
import nl.nfi.aardwolf.domain.griddata.FileDeltaToExperiment;
import nl.nfi.aardwolf.ui.service.FileSearchViewService;

public class FileSearchViewServiceTest {

    @Test
    public void testGetFilteredMainGridData() {
        final ExperimentToApp expToApp1 = new ExperimentToApp(EXPERIMENT_1, APP_1);
        final ExperimentToApp expToApp2 = new ExperimentToApp(EXPERIMENT_2, APP_2);

        final List<FileDeltaToExperiment> expectedData = Arrays.asList(
                                                                       new FileDeltaToExperiment(CREATED_FILE, Arrays.asList(expToApp1, expToApp2)),
                                                                       new FileDeltaToExperiment(MODIFIED_FILE, Arrays.asList(expToApp1)));

        final List<Triple<FileDelta, Experiment, App>> _dummyData = Arrays.asList(
                                                                                  Triple.of(CREATED_FILE, EXPERIMENT_1, APP_1),
                                                                                  Triple.of(MODIFIED_FILE, EXPERIMENT_1, APP_1),
                                                                                  Triple.of(CREATED_FILE, EXPERIMENT_2, APP_2));

        final List<FileDeltaToExperiment> actualData = FileSearchViewService.getMainGridData(_dummyData);
        assertThat(actualData, containsInAnyOrder(expectedData.toArray()));
    }

    @Test
    public void testGetFilteredMainGridData_dataShouldBeSorted() {
        final List<ExperimentToApp> expToApp = Arrays.asList(new ExperimentToApp(EXPERIMENT_1, APP_1));
        final List<Triple<FileDelta, Experiment, App>> _unsortedDummyData = Arrays.asList(
                                                                                          Triple.of(DELETED_FILE, EXPERIMENT_1, APP_1),
                                                                                          Triple.of(MODIFIED_FILE, EXPERIMENT_1, APP_1),
                                                                                          Triple.of(CREATED_FILE, EXPERIMENT_1, APP_1));

        final List<FileDeltaToExperiment> actualData = FileSearchViewService.getMainGridData(_unsortedDummyData);
        final List<FileDeltaToExperiment> expectedData = Arrays.asList(
                                                                       new FileDeltaToExperiment(CREATED_FILE, expToApp),
                                                                       new FileDeltaToExperiment(DELETED_FILE, expToApp),
                                                                       new FileDeltaToExperiment(MODIFIED_FILE, expToApp));

        assertThat(actualData, equalTo(expectedData));
    }

    @Test
    public void testGetFilteredMainGridData_filesWithTheSameTypeAndName() {
        final ExperimentToApp expToApp1 = new ExperimentToApp(EXPERIMENT_1, APP_1);
        final ExperimentToApp expToApp2 = new ExperimentToApp(EXPERIMENT_2, APP_1);

        final ModificationType type = ModificationType.MODIFIED;
        final FileDelta fileDelta1 = new FileDelta(type, "modifiedtype", (new File("path1", type.toString())).toString());
        final FileDelta fileDelta2 = new FileDelta(type, "modifiedtype", (new File("path2", type.toString())).toString());

        final List<FileDeltaToExperiment> expectedData = Arrays.asList(
                                                                       new FileDeltaToExperiment(fileDelta1, Arrays.asList(expToApp1)),
                                                                       new FileDeltaToExperiment(fileDelta2, Arrays.asList(expToApp2)));

        final List<Triple<FileDelta, Experiment, App>> _dummyData = Arrays.asList(
                                                                                  Triple.of(fileDelta1, EXPERIMENT_1, APP_1),
                                                                                  Triple.of(fileDelta2, EXPERIMENT_2, APP_1));

        final List<FileDeltaToExperiment> actualData = FileSearchViewService.getMainGridData(_dummyData);

        assertThat(actualData, containsInAnyOrder(expectedData.toArray()));
    }
}
