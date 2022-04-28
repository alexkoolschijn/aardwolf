/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLink;

import nl.nfi.aardwolf.security.SecurityUtils;
import nl.nfi.aardwolf.ui.appsearchview.AppSearchView;
import nl.nfi.aardwolf.ui.filesearchview.FileSearchView;

@CssImport("./styles/aardwolf/ui/main-view.css")
public class MainView extends AppLayout implements BeforeEnterObserver {

    private static final long serialVersionUID = 351412646668047564L;
    private final Image _rijksLogo;
    private final Image _appLogo;

    private final Tabs _tabs = new Tabs(true);
    private final Map<Class<? extends Component>, Tab> _navigationTargetToTab = new HashMap<>();

    public MainView() {
        _rijksLogo = new Image("images/logo-nfi.svg", "rijkslogo NFI");
        _rijksLogo.addClassName("rijksLogo");
        _rijksLogo.setId("rijkslogo");

        _appLogo = new Image("images/aardwolf.png", "Aardwolf Logo");
        _appLogo.addClassName("appLogo");
        _appLogo.setId("applogo");
        _appLogo.setTitle("Logo: Aardwolf by Icons Producer from NounProject.com");

        addMenuTab("Home", DefaultView.class);
        addMenuTab("App search", AppSearchView.class);
        addMenuTab("File search", FileSearchView.class);
        addMenuTab("Data export", ExportView.class);
        addMenuTab("Data import", ImportView.class);
        addMenuTab("Argus", ArgusView.class);
        addMenuTab("Help", HelpView.class);

        Button logout = new Button("Log out " + SecurityUtils.getUserDetails().getUsername(), VaadinIcon.EXIT.create());
        logout.setClassName("logout");
        logout.addClickListener(event -> UI.getCurrent().getPage().setLocation("/logout"));

        addToNavbar(true, _rijksLogo, _tabs, new Div(), logout, _appLogo);
    }

    private void addMenuTab(final String label, final Class<? extends Component> target) {
        final Tab tab = new Tab(new RouterLink(label, target));
        tab.setId(label);
        _navigationTargetToTab.put(target, tab);
        _tabs.add(tab);
    }

    @Override
    public void beforeEnter(final BeforeEnterEvent event) {
        _tabs.setSelectedTab(_navigationTargetToTab.get(event.getNavigationTarget()));
    }
}