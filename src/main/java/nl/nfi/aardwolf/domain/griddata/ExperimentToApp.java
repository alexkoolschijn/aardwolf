/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.domain.griddata;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;

public class ExperimentToApp extends Experiment {

    private final Experiment _experiment;
    private final App _app;

    public ExperimentToApp(final Experiment experiment, final App app) {
        super(experiment);
        _experiment = experiment;
        _app = app;
    }

    public Experiment getExperiment() {
        return _experiment;
    }

    public App getApp() {
        return _app;
    }
}
