/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;

// todo: if icon at top of page was missing at login screen before.
@Viewport("width=device-width, minimum-scale=1, initial-scale=0.5, user-scalable=yes, viewport-fit=cover")
@PWA(name = "Aardwolf", shortName = "Aardwolf", iconPath = "images/aardwolf.png", description = "Application Analysis Results Directory With Open-source Library Foundation")
public class AppShell implements AppShellConfigurator {
}
