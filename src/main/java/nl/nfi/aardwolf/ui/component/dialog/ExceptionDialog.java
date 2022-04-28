/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.dialog;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;

@CssImport("./styles/shared-styles.css")
@CssImport("./styles/aardwolf/ui/component/dialog/exception-dialog.css")
public class ExceptionDialog extends Dialog {

    private static final long serialVersionUID = 2642618347230619211L;
    private final String _title = "Exception";
    private final String _width = "50%";

    public Div getTitle() {
        final Div div = new Div(new Text(_title));
        div.addClassName("warningText");
        return div;
    }

    public Div getConfirmationButton() {
        final Div buttonContainer = new Div();
        final Button okButton = new Button("OK");
        okButton.addClickListener(okEvent -> {
            close();
        });
        buttonContainer.add(okButton);
        buttonContainer.addClassName("rightAlignedContainer");
        return buttonContainer;
    }

    public Span getText(final String message) {
        final Span span = new Span();
        span.getElement().setProperty("innerHTML", message);
        return span;
    }

    public ExceptionDialog(final String message) {
        super();

        final Div content = new Div();
        content.add(
                    getTitle(),
                    new Hr(),
                    getText(message),
                    getConfirmationButton());

        add(content);

        setWidth(_width);
    }
}