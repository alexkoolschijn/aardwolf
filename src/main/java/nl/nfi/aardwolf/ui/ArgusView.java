/*
 * Copyright (c) 2020, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Aardwolf | Argus")
@Route(value = "argus", layout = MainView.class)
public class ArgusView extends Div {
    private static final long serialVersionUID = 1160745136566984536L;
    private final static String TEXT_ID = "thisIsMyField";
    private final static String URL_STRING = "http://127.0.0.1:5000/test";
    private final static String ARGUS_REQUEST_TEMPLATE = "var xhr = new XMLHttpRequest(); xhr.open(\"GET\", $0, true); xhr.send(); "
        + "var text = document.getElementById($1);"
        + "xhr.onload = function(){text.innerHTML=xhr.responseText;};"
        + "xhr.onerror = function(){text.innerHTML=\"\"; alert(\"Something went wrong. Is the Argus API running? Open the console for details.\")};";

    public ArgusView() {
        final VerticalLayout vl = new VerticalLayout();
        add(vl);

        final Button button = new Button("Ping Argus");
        final Div text = new Div(new Text(""));
        text.setId(TEXT_ID);
        text.setClassName("successText");
        button.addClickListener(event -> {

            // Send a request from the browser to the Argus API on the user's work station
            final Page page = UI.getCurrent().getPage();
            page.executeJs(ARGUS_REQUEST_TEMPLATE, URL_STRING, TEXT_ID);
        });
        vl.add(button, text);
    }
}
