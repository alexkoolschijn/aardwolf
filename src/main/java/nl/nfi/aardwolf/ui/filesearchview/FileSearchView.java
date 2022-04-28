/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.filesearchview;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.griddata.ExperimentToApp;
import nl.nfi.aardwolf.domain.griddata.FileDeltaToExperiment;
import nl.nfi.aardwolf.repositories.AppRepository;
import nl.nfi.aardwolf.service.AppRepositoryService;
import nl.nfi.aardwolf.ui.MainView;
import nl.nfi.aardwolf.ui.component.GridWrapper;
import nl.nfi.aardwolf.ui.component.SearchComponent;
import nl.nfi.aardwolf.ui.component.grid.AppGridComponent;
import nl.nfi.aardwolf.ui.component.grid.DetailedAppGridComponent;
import nl.nfi.aardwolf.ui.component.grid.ExperimentsGridComponent;
import nl.nfi.aardwolf.ui.component.grid.FileDeltaGridComponent;
import nl.nfi.aardwolf.ui.component.grid.GridLayoutComponentFactory;
import nl.nfi.aardwolf.ui.component.text.HeaderComponent;
import nl.nfi.aardwolf.ui.service.FileSearchViewService;

@PageTitle("Aardwolf | File search")
@Route(value = "file_search", layout = MainView.class)
@Transactional
public class FileSearchView extends Div {
    private static final long serialVersionUID = -5863573623569494217L;

    private static final Logger LOG = LoggerFactory.getLogger(FileSearchView.class);

    @Autowired
    AppRepository _appRepo;

    final Runnable search = () -> performSearch();
    private final SearchComponent _controls = new SearchComponent(search);
    private final FileSearchViewGridManager _grids;

    public FileSearchView(final AppRepository appRepo) {

        final AppGridComponent appGrid = createApplicationGrid();
        final ExperimentsGridComponent<ExperimentToApp> experimentsGrid = createExperimentsGrid(appGrid);
        final FileDeltaGridComponent<FileDeltaToExperiment> fileDeltaGrid = createFileDeltaGrid(experimentsGrid);
        _grids = new FileSearchViewGridManager(appGrid, experimentsGrid, fileDeltaGrid);

        final SplitLayout layout = new GridLayoutComponentFactory<FileDeltaToExperiment, ExperimentToApp, App>()
            .getLayout(
                       new GridWrapper<>(new HeaderComponent(FileDeltaGridComponent.HEADER), fileDeltaGrid),
                       new GridWrapper<>(new HeaderComponent(ExperimentsGridComponent.HEADER), experimentsGrid),
                       new GridWrapper<>(new HeaderComponent(AppGridComponent.HEADER), appGrid),
                       _controls);
        add(layout);
        setHeightFull();
    }

    private void performSearch() {
        final String criterion = _controls.getTextField().getValue();
        LOG.info("Performing search for criterion: {}", criterion);
        if (criterion.isEmpty()) {
            _grids.getFileDeltaGrid().setItems();
        }
        else {

            final List<FileDeltaToExperiment> fileDeltas = FileSearchViewService
                .getMainGridData(FileSearchViewService
                    .filterData(AppRepositoryService
                        .getFiledeltaExperimentAppStream(_appRepo), criterion)
                    .collect(Collectors.toList()));

            _grids.getFileDeltaGrid().setItems(fileDeltas);
            if (fileDeltas.isEmpty()) {
                Notification.show("Query term '" + "%" + criterion + "%" + "' did not result in any results");
            }
        }
    }

    private AppGridComponent createApplicationGrid() {
        return new DetailedAppGridComponent();
    }

    private ExperimentsGridComponent<ExperimentToApp> createExperimentsGrid(final Grid<App> appGrid) {
        final ExperimentsGridComponent<ExperimentToApp> experimentsGrid = new ExperimentsGridComponent<ExperimentToApp>(ExperimentToApp.class);
        experimentsGrid.addSelectionListener(evt -> {
            _grids.updateAppGrid(evt.getFirstSelectedItem().orElse(null), _appRepo);
        });
        return experimentsGrid;
    }

    private FileDeltaGridComponent<FileDeltaToExperiment> createFileDeltaGrid(final Grid<ExperimentToApp> experimentGrid) {
        final FileDeltaGridComponent<FileDeltaToExperiment> fileDeltaGrid = new FileDeltaGridComponent<FileDeltaToExperiment>(FileDeltaToExperiment.class);

        fileDeltaGrid.addSelectionListener(evt -> {
            _grids.updateExperimentsGrid(evt.getFirstSelectedItem().orElse(null), _appRepo);
        });
        return fileDeltaGrid;
    }
}
