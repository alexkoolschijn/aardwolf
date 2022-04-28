/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Aardwolf | Export")
@Route(value = "export", layout = MainView.class)
public class ExportView extends Div {
    private static final long serialVersionUID = 7074119309909251461L;

    public ExportView() {
        add(new Span("Export view content"));
    }
}