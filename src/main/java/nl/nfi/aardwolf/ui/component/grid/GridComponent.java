/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSingleSelectionModel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;

import nl.nfi.aardwolf.ui.component.DetailsComponent;
import nl.nfi.aardwolf.ui.component.DetailsDropdown;
import nl.nfi.aardwolf.ui.component.GridColumnMetadata;
import nl.nfi.aardwolf.ui.component.RecordDetail;

public abstract class GridComponent<T>extends Grid<T> {
    private static final long serialVersionUID = -299553307906028847L;

    /**
     * Extension of the Vaadin Grid that adds simple columns,
     * allows for a column ordering and applies a couple of default settings.
     * @param beanType the class of the bean
     * @param simpleColumns a {@link List} of {@link String}s containing the column names
     * @param columnOrder a {@link List} of column names in the desired order
     * @param id the ID of the grid component in the DOM
     */
    public GridComponent(
                         final Class<T> beanType,
                         final List<String> simpleColumns,
                         final List<String> columnOrder,
                         final String id) {
        super(beanType);
        build(simpleColumns, columnOrder);
        setId(id);
    }

    public GridComponent(
                         final Class<T> beanType,
                         final List<String> columns,
                         final String id) {
        this(beanType, columns, columns, id);
    }

    public GridComponent(final Class<T> beanType, final String id) {
        this(beanType, new ArrayList<String>(), new ArrayList<String>(), id);
    }

    protected void addCustomColumns() {
    }

    protected List<RecordDetail> descriptions(final T item) {
        return new ArrayList<RecordDetail>();
    }

    protected void setDetailsRenderer() {
        setItemDetailsRenderer(new ComponentRenderer<VerticalLayout, T>(item -> {
            final DetailsComponent details = new DetailsComponent();
            details.addNonNullDescriptions(descriptions(item));
            return details;
        }));
    }

    protected void addDetailsColumn(final GridColumnMetadata columnMetadata, final Function<T, Component> getItemText) {
        addComponentColumn(new ValueProvider<T, Div>() {
            private static final long serialVersionUID = 6022074468858872432L;

            @Override
            public Div apply(final T item) {
                final boolean detailsVisible = isDetailsVisible(item);
                return new DetailsDropdown(detailsVisible,
                                           event -> setDetailsVisible(item, !detailsVisible),
                                           getItemText.apply(item));
            }
        }).setHeader(columnMetadata.getHeader()).setSortable(true).setKey(columnMetadata.getKey());
        setDetailsRenderer();
    }

    @SuppressWarnings("unchecked")
    private void build(final List<String> simpleColumns, final List<String> orderedColumns) {
        removeAllColumns();
        addColumns(simpleColumns);
        addCustomColumns();
        applySettings();

        @SuppressWarnings("rawtypes")
        final List<Column> cols = orderedColumns.stream().map(key -> getColumnByKey(key)).collect(Collectors.toList());
        setColumnOrder(cols.toArray(new Column[0]));
    }

    private void addColumns(final List<String> simpleColumns) {
        for (final String column : simpleColumns) {
            addColumns(column);
        }
    }

    protected void applySettings() {
        setColumnReorderingAllowed(true);
        getColumns().stream().forEach(column -> column.setResizable(true));
        setDetailsVisibleOnClick(false);
        setSelectionMode(Grid.SelectionMode.SINGLE);
        ((GridSingleSelectionModel<T>) getSelectionModel()).setDeselectAllowed(false);
    }
}
