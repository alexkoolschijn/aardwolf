/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login") 
@PageTitle("Login | Aardwolf")
@CssImport("./styles/aardwolf/ui/login-view.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();
    
    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER); 
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");  
        login.addForgotPasswordListener(evt -> UI.getCurrent().getPage().setLocation("/forgotpassword"));

        Span version = new Span(getClass().getPackage().getImplementationVersion());
        
        Image applogo = new Image("images/aardwolf.png", "Aardwolf Logo");
        applogo.setClassName("applogo");
        applogo.setAlt("Aardwolf logo");
        applogo.setTitle("Logo: Aardwolf by Icons Producer from NounProject.com");
        add(applogo, version, login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation() 
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
    }
}
