/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component;

/**
 * Class that represents a single detail of a data record
 */
public class RecordDetail {
    private final String _title;
    private final String _description;

    public RecordDetail(final String title, final String description) {
        _title = title;
        _description = description;
    }

    public String getTitle() {
        return _title;
    }

    public String getDescription() {
        return _description;
    }
}