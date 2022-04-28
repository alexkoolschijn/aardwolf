/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.grid;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.function.ValueProvider;

import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.helpers.JsonHelper;
import nl.nfi.aardwolf.ui.component.GridColumnMetadata;
import nl.nfi.aardwolf.ui.component.RecordDetail;

public class ExperimentsGridComponent<T extends Experiment>extends GridComponent<T> {
    private static final long serialVersionUID = -3408719292643679424L;
    public static final String ID = "ExperimentsGrid";

    public static final String HEADER = "Experiments";
    private static final GridColumnMetadata DETAILS_COLUMN = new GridColumnMetadata("component", "Date Time Performed");
    private static final GridColumnMetadata DEVICE_COLUMN = new GridColumnMetadata("product_model", "Device Model");
    private static final GridColumnMetadata BRAND_COLUMN = new GridColumnMetadata("product_brand", "Device Brand");
    private static final String OS_KEY = "operatingSystem";
    private static final List<String> SIMPLE_COLUMNS = Arrays.asList(OS_KEY);
    private static final List<String> COLUMN_ORDER = Arrays.asList(
                                                                   DETAILS_COLUMN.getKey(),
                                                                   OS_KEY,
                                                                   DEVICE_COLUMN.getKey(),
                                                                   BRAND_COLUMN.getKey());

    public ExperimentsGridComponent(final Class<T> experimentClass) {
        super(experimentClass, SIMPLE_COLUMNS, COLUMN_ORDER, ID);
    }

    @Override
    protected List<RecordDetail> descriptions(final T item) {
        final Experiment experiment = item;
        return Arrays.asList(
                             new RecordDetail("Device Details", experiment.getDeviceDetails()),
                             new RecordDetail("User Name", experiment.getUserName()),
                             new RecordDetail("Method Details", experiment.getMethodDetails()),
                             new RecordDetail("Script", experiment.getScript()));
    }

    @Override
    protected void addCustomColumns() {
        addDetailsColumn(DETAILS_COLUMN, (final T experiment) -> new Text(experiment.getDateTimePerformed().toString()));
        addDeviceDetails();

    }

    protected void addDeviceDetails() {
        addDeviceColumn(DEVICE_COLUMN);
        addDeviceColumn(BRAND_COLUMN);
    }

    private void addDeviceColumn(final GridColumnMetadata columnData) {
        addColumn(new ValueProvider<T, String>() {
            private static final long serialVersionUID = -6831740439586488306L;

            @Override
            public String apply(final T exp) {
                return JsonHelper.jsonGetProperty(columnData.getKey(), exp.getDeviceDetails());
            }
        }).setHeader(columnData.getHeader()).setKey(columnData.getKey());
    }

    @Override
    protected void applySettings() {
        super.applySettings();
        setHeightFull();
    }
}
