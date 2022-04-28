/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component;

import com.vaadin.flow.component.html.Div;

import nl.nfi.aardwolf.ui.component.grid.GridComponent;

/**
 * Wrapper that contains a Grid and its header object
 * @param <T> The type stored in the embedded {@link GridComponent}
 */
public class GridWrapper<T> {
    private final Div _header;
    private final GridComponent<T> _grid;

    public GridWrapper(final Div header, final GridComponent<T> grid) {
        _header = header;
        _grid = grid;
    }

    public Div getHeader() {
        return _header;
    }

    public GridComponent<T> getGrid() {
        return _grid;
    }
}
