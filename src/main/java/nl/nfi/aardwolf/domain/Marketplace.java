/**
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.domain;

public enum Marketplace {
    OTHER("Other", "OtherStore"),
    APPSTORE("App Store", "AppStore"),
    PLAYSTORE("Play Store", "PlayStore");

    private String _friendlyName;
    private String _id;

    Marketplace(final String friendlyName, final String id) {
        _friendlyName = friendlyName;
        _id = id;
    }

    @Override
    public String toString() {
        return _friendlyName;
    }

    public String getId() {
        return _id;
    }

    public String getFriendlyName() {
        return _friendlyName;
    }
}