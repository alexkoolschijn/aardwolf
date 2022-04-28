/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.filesearchview;

import java.util.List;

import nl.nfi.aardwolf.domain.griddata.ExperimentToApp;
import nl.nfi.aardwolf.domain.griddata.FileDeltaToExperiment;
import nl.nfi.aardwolf.repositories.AppRepository;
import nl.nfi.aardwolf.ui.component.grid.AppGridComponent;
import nl.nfi.aardwolf.ui.component.grid.ExperimentsGridComponent;
import nl.nfi.aardwolf.ui.component.grid.FileDeltaGridComponent;
import nl.nfi.aardwolf.ui.component.grid.GridManager;

public class FileSearchViewGridManager extends GridManager<ExperimentToApp> {
    private FileDeltaGridComponent<FileDeltaToExperiment> _fileDeltaGrid;

    public FileSearchViewGridManager(
                                     final AppGridComponent appGrid,
                                     final ExperimentsGridComponent<ExperimentToApp> experimentsGrid,
                                     final FileDeltaGridComponent<FileDeltaToExperiment> fileDeltaGrid) {
        super(appGrid, experimentsGrid);
        _fileDeltaGrid = fileDeltaGrid;
    }

    public void updateExperimentsGrid(final FileDeltaToExperiment fileDeltaEntry, final AppRepository appRepo) {
        if (fileDeltaEntry != null) {
            final List<ExperimentToApp> experiments = fileDeltaEntry.getExperiments();
            _experimentsGrid.setItems(experiments);
            if (!experiments.isEmpty()) {
                _experimentsGrid.select(experiments.get(0));
            }
        }
        else {
            _experimentsGrid.setItems();
        }
    }

    public void updateAppGrid(final ExperimentToApp experimentEntry, final AppRepository appRepo) {
        if (experimentEntry != null) {
            _appGrid.setItems(experimentEntry.getApp());
        }
        else {
            _appGrid.setItems();
        }
    }

    /**
     * @return
     */
    public FileDeltaGridComponent<FileDeltaToExperiment> getFileDeltaGrid() {
        return _fileDeltaGrid;
    }
}
