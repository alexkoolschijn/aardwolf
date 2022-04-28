/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.service;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.domain.FileDelta;
import nl.nfi.aardwolf.domain.griddata.ExperimentToApp;
import nl.nfi.aardwolf.domain.griddata.FileDeltaToExperiment;

public class FileSearchViewService {

    public static Stream<Triple<FileDelta, Experiment, App>> filterData(final Stream<Triple<FileDelta, Experiment, App>> stream, final String criterion) {
        return stream
            .filter(triplet -> triplet.getLeft().getFileName().toLowerCase().contains(criterion.toLowerCase()));
    }

    public static List<FileDeltaToExperiment> getMainGridData(final List<Triple<FileDelta, Experiment, App>> flatTripletList) {

        return flatTripletList
            .stream()
            .map(triplet -> Pair.of(triplet.getLeft(), new ExperimentToApp(triplet.getMiddle(), triplet.getRight())))
            .collect(groupingBy(Pair::getLeft))
            .entrySet()
            .stream()
            .sorted((a, b) -> a.getKey().getFileName().compareTo(b.getKey().getFileName()))
            .map(entry -> new FileDeltaToExperiment(entry.getValue().get(0).getLeft(),
                                                    entry.getValue()
                                                        .stream()
                                                        .map(item -> item.getRight())
                                                        .collect(Collectors.toList())))
            .collect(Collectors.toList());
    }
}
