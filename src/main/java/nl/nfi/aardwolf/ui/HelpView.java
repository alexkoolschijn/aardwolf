/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Aardwolf | Help")
@Route(value = "help", layout = MainView.class)
public class HelpView extends Div {
    private static final long serialVersionUID = 1L;

    public HelpView() {
        final VerticalLayout vl = new VerticalLayout();
        add(vl);

        vl.add(new H1("Aardwolf"));
        vl.add(new Span(getClass().getPackage().getImplementationVersion()));

        vl.add(new Span("This will show the help for AARDWOLF"));
        
        vl.add(new Span("Logo: Aardwolf by Icons Producer from NounProject.com"));
    }
}