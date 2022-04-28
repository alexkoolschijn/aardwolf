/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport.argus.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArgusSystem {
    public String device_type;
    public String device_name;
    @JsonProperty(value="OS_version")
    public String os_version;
    public String product_brand;
    public String product_model;
    public String serial;
    public String sdk_version;
    public String is_emulator;
}
