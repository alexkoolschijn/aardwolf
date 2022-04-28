/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport.argus.metadata;

import java.util.Map;

public class ArgusDevice {
    public ArgusSystem system;
    public Map<String,ArgusAppMetadata> apps;
}
