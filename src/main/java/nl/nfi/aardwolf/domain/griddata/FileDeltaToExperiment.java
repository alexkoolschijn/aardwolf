/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.domain.griddata;

import java.util.List;

import nl.nfi.aardwolf.domain.FileDelta;

public class FileDeltaToExperiment extends FileDelta {
    private final FileDelta _fileDelta;
    private final List<ExperimentToApp> _experiments;

    public FileDeltaToExperiment(final FileDelta fileDelta, final List<ExperimentToApp> experiments) {
        super(fileDelta);
        _fileDelta = fileDelta;
        _experiments = experiments;
    }

    public FileDelta getFileDelta() {
        return _fileDelta;
    }

    public List<ExperimentToApp> getExperiments() {
        return _experiments;
    }
}
