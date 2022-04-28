/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.mongo;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;

public class RecordsPanel extends FormLayout {
    private static final long serialVersionUID = -2988434812667048208L;
    private int _records;
    private final String _label;
    private final Text _recordsText;

    public RecordsPanel(final int records, final String label, final String id) {
        _records = records;
        _label = label;
        _recordsText = new Text(String.valueOf(records));
        addFormItem(_recordsText, _label);
        setId(id);
    }

    public void setRecords(final int records) {
        _records = records;
        _recordsText.setText(String.valueOf(_records));
    }

    public int getRecords() {
        return _records;
    }

    public String getLabel() {
        return _label;
    }

    public Text getRecordsText() {
        return _recordsText;
    }
}
