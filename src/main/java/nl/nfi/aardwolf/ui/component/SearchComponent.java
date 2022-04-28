/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class SearchComponent extends HorizontalLayout {
    private static final long serialVersionUID = -4436525582069327850L;
    private TextField _nameSearchField;
    protected final Runnable _search;
    protected final static String TEXT_FIELD_ID = "SearchComponent";
    protected final static String SEARCH_BUTTON_ID = "SearchButton";

    public SearchComponent(final Runnable search) {
        _search = search;
        addComponents();
    }

    protected void addComponents() {
        addTextField();
        addSearchButton();
    }

    public void addTextField() {
        _nameSearchField = new TextField("Search for");
        _nameSearchField.setPlaceholder("Enter search value");
        _nameSearchField.setClearButtonVisible(true);
        _nameSearchField.addValueChangeListener(event -> {
            _search.run();
        });
        _nameSearchField.setId(TEXT_FIELD_ID);
        setSettings();

        add(_nameSearchField);
    }

    public TextField getTextField() {
        return _nameSearchField;
    }

    private void setSettings() {
        setAlignItems(Alignment.BASELINE);
        setSpacing(true);
    }

    protected void addSearchButton() {
        final Button searchButton = new Button("Search", VaadinIcon.SEARCH.create());
        searchButton.setId(SEARCH_BUTTON_ID);
        searchButton.addClickListener(e -> {
            _search.run();
        });
        add(searchButton);
    }
}
