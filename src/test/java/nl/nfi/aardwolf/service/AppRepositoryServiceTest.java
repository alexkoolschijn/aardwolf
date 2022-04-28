/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Triple;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.domain.FileDelta;
import nl.nfi.aardwolf.helper.DummyData;
import nl.nfi.aardwolf.repositories.AppRepository;
import nl.nfi.aardwolf.service.AppRepositoryService;



public class AppRepositoryServiceTest {

    @Test
    public void testGetFiledeltaExperimentAppStream() {

        final App app1 = DummyData.APP_1;
        final App app2 = DummyData.APP_2;
        final Experiment exp1 = DummyData.EXPERIMENT_1;
        final Experiment exp2 = DummyData.EXPERIMENT_2;
        final FileDelta fileDelta1 = DummyData.CREATED_FILE;
        final FileDelta fileDelta2 = DummyData.MODIFIED_FILE;
        exp1.setChanges(List.of(fileDelta1));
        exp2.setChanges(Arrays.asList(fileDelta1, fileDelta2));
        app1.setExperiments(Arrays.asList(exp1, exp2));
        app2.setExperiments(List.of(exp1));

        final AppRepository dummyRepository = mock(AppRepository.class);
        when(dummyRepository.findAll()).thenReturn(Arrays.asList(app1, app2));

        final List<Triple<FileDelta, Experiment, App>> expectedData = Arrays.asList(
                                                                                    Triple.of(fileDelta1, exp1, app1),
                                                                                    Triple.of(fileDelta1, exp1, app2),
                                                                                    Triple.of(fileDelta1, exp2, app1),
                                                                                    Triple.of(fileDelta2, exp2, app1)

        );

        final List<Triple<FileDelta, Experiment, App>> actualData = AppRepositoryService
            .getFiledeltaExperimentAppStream(dummyRepository)
            .collect(Collectors.toList());

        MatcherAssert.assertThat(actualData, containsInAnyOrder(expectedData.toArray()));
    }
}
