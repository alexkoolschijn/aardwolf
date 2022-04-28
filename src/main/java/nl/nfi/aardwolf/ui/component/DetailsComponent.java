/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component;

import java.util.List;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import nl.nfi.aardwolf.ui.component.text.LabelComponent;

public class DetailsComponent extends VerticalLayout {
    private static final long serialVersionUID = -6159417766069924448L;

    public void addDescription(final RecordDetail description) {
        final LabelComponent label = new LabelComponent(description.getTitle());
        add(label);
        add(description.getDescription());
    }

    public void addNonNullDescriptions(final List<RecordDetail> descriptions) {
        descriptions
            .stream()
            .filter(description -> description.getDescription() != null)
            .forEach(description -> addDescription(description));
    }
}