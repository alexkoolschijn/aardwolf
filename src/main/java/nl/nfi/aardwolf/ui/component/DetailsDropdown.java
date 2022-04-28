/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class DetailsDropdown extends Div {

    private static final long serialVersionUID = -8220957836834058523L;
    private static int _iconSizePx = 16;

    public DetailsDropdown(final boolean visible,
                           final ComponentEventListener<ClickEvent<Icon>> listener,
                           final Component textComponent) {
        final Icon icon = new Icon(visible ? VaadinIcon.CHEVRON_DOWN_SMALL : VaadinIcon.CHEVRON_RIGHT_SMALL);
        icon.setSize(String.format("%spx", _iconSizePx));
        icon.addClickListener(listener);
        add(icon, textComponent);
    }
}
