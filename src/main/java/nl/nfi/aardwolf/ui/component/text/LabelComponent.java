/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.text;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;

@CssImport("./styles/aardwolf/ui/component/text/label-component.css")
public class LabelComponent extends Label {
    private static final long serialVersionUID = 3414904422287047941L;

    public LabelComponent(final String text, final String... additionalClasses) {
        super(text);

        for (final String className : additionalClasses) {
            addClassName(className);
        }
    }
}
