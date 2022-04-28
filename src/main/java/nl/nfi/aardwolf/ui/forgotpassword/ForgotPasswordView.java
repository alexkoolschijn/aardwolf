/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.forgotpassword;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("forgotpassword") 
@PageTitle("Forgot Password | Aardwolf")
public class ForgotPasswordView extends VerticalLayout{

    public ForgotPasswordView() {
        // TODO: AARDWOLF-58: Create functionality for this page
        add(new H1("Forgot password"));
    }
    
}
