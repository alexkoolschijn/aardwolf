/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.appsearchview;

import java.util.EnumSet;

import com.vaadin.flow.component.checkbox.CheckboxGroup;

import nl.nfi.aardwolf.domain.Marketplace;
import nl.nfi.aardwolf.ui.component.SearchComponent;

public class AppSearchViewSearchComponent extends SearchComponent {
    private static final long serialVersionUID = 8186289525914730197L;
    private CheckboxGroup<Marketplace> _storeGroup;

    public AppSearchViewSearchComponent(final Runnable search) {
        super(search);
    }

    @Override
    protected void addComponents() {
        addTextField();
        addCheckboxGroup();
        addSearchButton();
    }

    private void addCheckboxGroup() {
        _storeGroup = new CheckboxGroup<>();
        _storeGroup.setLabel("Marketplace");
        _storeGroup.setItems(Marketplace.values());
        _storeGroup.setValue(EnumSet.allOf(Marketplace.class));
        _storeGroup.addSelectionListener(event -> _search.run());
        add(_storeGroup);
    }

    public CheckboxGroup<Marketplace> getStoreGroup() {
        return _storeGroup;
    }
}
