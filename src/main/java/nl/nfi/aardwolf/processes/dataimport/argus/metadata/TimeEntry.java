/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport.argus.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeEntry {
    @JsonProperty("unix (ms)")
    public long unixTime;
    public String utc;
}