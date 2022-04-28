/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;

import nl.nfi.aardwolf.ui.component.grid.ExperimentsGridComponent;
import nl.nfi.aardwolf.ui.component.text.HeaderComponent;

public class ExperimentsHeaderComponent extends Div {
    private static final long serialVersionUID = -9125640655497528699L;

    public ExperimentsHeaderComponent(final Upload upload) {
        add(new HorizontalLayout(new HeaderComponent(ExperimentsGridComponent.HEADER, "fullWidth"), upload));
        setWidthFull();
    }
}
