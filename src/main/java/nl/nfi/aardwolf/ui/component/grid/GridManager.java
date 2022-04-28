/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.grid;

import nl.nfi.aardwolf.domain.Experiment;

/**
 * Class to manage the different Vaadin Grids available on a page
 */
public class GridManager<T extends Experiment> {
    protected final AppGridComponent _appGrid;
    protected final ExperimentsGridComponent<T> _experimentsGrid;

    public GridManager(final AppGridComponent appGrid,
                       final ExperimentsGridComponent<T> experimentsGrid) {
        _appGrid = appGrid;
        _experimentsGrid = experimentsGrid;
    }

    public AppGridComponent getAppGrid() {
        return _appGrid;
    }

    public ExperimentsGridComponent<T> getExperimentsGrid() {
        return _experimentsGrid;
    }
}
