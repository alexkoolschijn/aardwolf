/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.text;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;

@CssImport("./styles/aardwolf/ui/component/text/header-component.css")
public class HeaderComponent extends Div {
    /**
     * Simple Header Div
     */
    private static final long serialVersionUID = -7443453804600940743L;
    private final String _className = "header";

    public HeaderComponent(final String text, final String... additionalClasses) {
        add(new Text(text));
        addClassName(_className);
        for (final String className : additionalClasses) {
            addClassName(className);
        }
    }
}