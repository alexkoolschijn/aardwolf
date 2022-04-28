/*
 * All rights reserved.
 */
package nl.nfi.aardwolf.service;

import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Triple;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.domain.FileDelta;
import nl.nfi.aardwolf.repositories.AppRepository;

public class AppRepositoryService {

    /**
     * Return app data as a flat record stream
     * @param appRepository an {@link AppRepository} supplying {@link App} records
     * @return flat record stream of type Triplet&lt;{@link FileDelta}, {@link Experiment}, {@link App}&gt;
     */
    public static Stream<Triple<FileDelta, Experiment, App>> getFiledeltaExperimentAppStream(final AppRepository appRepository) {
        return appRepository.findAll()
            .stream()
            .flatMap(app -> app.getExperiments()
                .stream()
                .flatMap(exp -> exp.getChanges()
                    .stream()
                    .map(delta -> Triple.of(delta, exp, app))));

    }
}
