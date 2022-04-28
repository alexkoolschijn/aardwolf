/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component;

/**
 * Class that represents metadata of a column in a Vaadin Grid
 */
public class GridColumnMetadata {
    private final String _key;
    private final String _header;

    public GridColumnMetadata(final String key, final String header) {
        _key = key;
        _header = header;
    }

    public String getKey() {
        return _key;
    }

    public String getHeader() {
        return _header;
    }
}
