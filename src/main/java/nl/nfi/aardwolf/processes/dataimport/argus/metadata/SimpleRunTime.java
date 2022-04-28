/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport.argus.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleRunTime {
    public TimeEntry start;
    public TimeEntry end;
    @JsonProperty(value = "duration (s)")
    public int duration;
}