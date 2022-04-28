/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.appsearchview;

import java.util.List;

import com.vaadin.flow.component.upload.Upload;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.ui.component.grid.AppGridComponent;
import nl.nfi.aardwolf.ui.component.grid.ExperimentsGridComponent;
import nl.nfi.aardwolf.ui.component.grid.GridManager;

public class AppSearchViewGridManager extends GridManager<Experiment> {

    public AppSearchViewGridManager(
                                    final AppGridComponent appGrid,
                                    final ExperimentsGridComponent<Experiment> experimentsGrid) {
        super(appGrid, experimentsGrid);
    }

    public void updateExperimentsGrid(final App app, final Upload upload) {
        if (app != null) {
            final List<Experiment> experiments = app.getExperiments();
            _experimentsGrid.setItems(experiments);
            if (!experiments.isEmpty()) {
                _experimentsGrid.select(experiments.get(0));
            }
            upload.setVisible(true);
        }
        else {
            getExperimentsGrid().setItems();
            upload.setVisible(false);
        }
    }
}
