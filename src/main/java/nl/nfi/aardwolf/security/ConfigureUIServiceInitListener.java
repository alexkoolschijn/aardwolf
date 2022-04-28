/*
 * Copyright (c) 2021 Netherlands Forensic Institute
 * All rights reserved.
 */

package nl.nfi.aardwolf.security;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import nl.nfi.aardwolf.ui.forgotpassword.ForgotPasswordView;
import nl.nfi.aardwolf.ui.login.LoginView;

@Component 
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> { 
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent event) {
        // Reroute navigation to the login page unless:
        // * we are already logged in
        // * navigation target is already the login page
        // * navigation target is the Forgot Password page
        if (!LoginView.class.equals(event.getNavigationTarget())
            && !ForgotPasswordView.class.equals(event.getNavigationTarget())
            && !SecurityUtils.isUserLoggedIn()) { 
            event.rerouteTo(LoginView.class);
        }
    }
}