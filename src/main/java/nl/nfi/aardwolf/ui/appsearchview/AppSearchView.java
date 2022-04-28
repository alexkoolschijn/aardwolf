/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.appsearchview;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.domain.FileDelta;
import nl.nfi.aardwolf.repositories.AppRepository;
import nl.nfi.aardwolf.ui.MainView;
import nl.nfi.aardwolf.ui.component.ExperimentsHeaderComponent;
import nl.nfi.aardwolf.ui.component.GridWrapper;
import nl.nfi.aardwolf.ui.component.UploadComponent;
import nl.nfi.aardwolf.ui.component.grid.AppGridComponent;
import nl.nfi.aardwolf.ui.component.grid.DetailedAppGridComponent;
import nl.nfi.aardwolf.ui.component.grid.ExperimentsGridComponent;
import nl.nfi.aardwolf.ui.component.grid.FileDeltaGridComponent;
import nl.nfi.aardwolf.ui.component.grid.GridLayoutComponentFactory;
import nl.nfi.aardwolf.ui.component.text.HeaderComponent;


@PageTitle("Aardwolf | App search")
@Route(value = "app_search", layout = MainView.class)
@Transactional
public class AppSearchView extends Div {
    private static final long serialVersionUID = 6655699423890955236L;

    private static final Logger LOG = LoggerFactory.getLogger(AppSearchView.class);

    @Autowired
    AppRepository _appRepo;

    private final AppSearchViewGridManager _grids;
    final Runnable search = () -> performSearch();
    private final AppSearchViewSearchComponent _controls = new AppSearchViewSearchComponent(search);
    private final Upload _upload;

    public AppSearchView(final AppRepository appRepo) {
        final ExperimentsGridComponent<Experiment> experimentsGrid = createExperimentsGrid();
        final AppGridComponent appGrid = createAppGridComponent();
        _grids = new AppSearchViewGridManager(appGrid, experimentsGrid);
        _upload = new UploadComponent(_grids, appRepo);

        @SuppressWarnings({"rawtypes", "unchecked"})
        final SplitLayout layout = new GridLayoutComponentFactory()
            .getLayout(
                       new GridWrapper<App>(new HeaderComponent(AppGridComponent.HEADER), appGrid),
                       new GridWrapper<Experiment>(new ExperimentsHeaderComponent(_upload), experimentsGrid),
                       _controls);
        add(layout);
        setHeightFull();
    }

    private AppGridComponent createAppGridComponent() {
        final AppGridComponent grid = new DetailedAppGridComponent();
        grid.addSelectionListener(evt -> {
            _grids.updateExperimentsGrid(
                                         evt.getFirstSelectedItem().orElse(null),
                                         _upload);
        });
        return grid;
    }

    private Collection<App> getFilteredMainGridData(final String criterion) {
        return _appRepo
            .findByNameLikeIgnoreCaseOrVersionLikeIgnoreCaseOrPublisherLikeIgnoreCaseOrDescriptionLikeIgnoreCase(
                                                                                                                 criterion,
                                                                                                                 criterion,
                                                                                                                 criterion,
                                                                                                                 criterion)
            .stream()
            .filter(app -> _controls.getStoreGroup().isSelected(app.getMarketplace())).collect(toList());
    }

    private void performSearch() {
        final String criterion =  "%" +_controls.getTextField().getValue() + "%";
        LOG.info("Performing search for criterion: {}", criterion);
        if (criterion.equals("%%")) {
            _grids.getAppGrid().setItems(Collections.emptyList());
        }
        else {
            final Collection<App> apps = getFilteredMainGridData(criterion);
            _grids.getAppGrid().setItems(apps);
            if (apps.isEmpty()) {
                Notification.show("Query term '" + criterion + "' did not result in any results");
            }
        }
    }

    private ExperimentsGridComponent<Experiment> createExperimentsGrid() {
        final ExperimentsGridComponent<Experiment> grid = new ExperimentsGridComponent<Experiment>(Experiment.class);
//        grid.addSelectionListener(evt -> {
//            _grids.updateFileDeltaGrid(evt.getFirstSelectedItem().orElse(null));
//        });
        return grid;
    }

}
