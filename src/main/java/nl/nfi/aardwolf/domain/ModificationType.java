/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.domain;

public enum ModificationType {
    CREATED, MODIFIED, DELETED;

    /**
     * Attempts to create a {@code ModificationType} instance based on the supplied name. If the name 
     * does not correspond to a known modification type, the upper case version of the name is tried.
     * 
     * @param name the name of the requested {@code ModificationType}
     * @return a {@code ModificationType} instance
     */
    public static ModificationType fuzzyValueOf(String name) {
        try {
            return valueOf(name);
        }
        catch (IllegalArgumentException iae) {
            return valueOf(name.toUpperCase());
        }
    }
}
