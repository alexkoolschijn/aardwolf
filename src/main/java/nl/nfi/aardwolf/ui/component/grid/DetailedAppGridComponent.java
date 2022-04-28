/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.grid;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.function.ValueProvider;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.ui.component.GridColumnMetadata;

public class DetailedAppGridComponent extends AppGridComponent {
    private static final long serialVersionUID = 1691561432209437823L;
    final static String EXPERIMENT_KEY = "experiments";
    final static GridColumnMetadata DETAILS_COLUMN = new GridColumnMetadata(APP_KEY, "App Name");
    final static GridColumnMetadata EXPERIMENT_COLUMN = new GridColumnMetadata(EXPERIMENT_KEY, "Number of experiments");
    private static List<String> _simpleCols = Arrays.asList(VERSION_KEY, PUBLISH_KEY, MARKETPLACE_KEY);
    private static List<String> _orderedCols = Arrays.asList(
                                                             DETAILS_COLUMN.getKey(),
                                                             VERSION_KEY,
                                                             PUBLISH_KEY,
                                                             MARKETPLACE_KEY,
                                                             EXPERIMENT_COLUMN.getKey());

    public DetailedAppGridComponent() {
        super(_simpleCols, _orderedCols);
    }

    @Override
    protected void addCustomColumns() {
        addDetailsColumn(DETAILS_COLUMN, (final App app) -> new Text(app.getName()));
        addExperimentsColumn();
    }

    protected void addExperimentsColumn() {
        addColumn(new ValueProvider<App, Integer>() {
            private static final long serialVersionUID = -7474038079352126581L;

            @Override
            public Integer apply(final App app) {
                if (app.getExperiments() == null) {
                    return 0;
                }
                return app.getExperiments().size();
            }
        }).setHeader(EXPERIMENT_COLUMN.getHeader()).setKey(EXPERIMENT_COLUMN.getKey());
    }
}
