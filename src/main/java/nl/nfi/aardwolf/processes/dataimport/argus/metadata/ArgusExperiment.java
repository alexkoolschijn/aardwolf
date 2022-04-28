/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport.argus.metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgusExperiment {
    public class ArgusMethod {
        public String name;
        public String[] search_dir;
    }
    public ArgusMethod method;
    public String name;
    public String path;
    public String description;

    @JsonProperty(value = "0")
    public ArgusRun before;
    
    @JsonProperty(value = "1")
    public ArgusRun after;
}
