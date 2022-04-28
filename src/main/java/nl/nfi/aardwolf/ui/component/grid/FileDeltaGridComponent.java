/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.grid;

import java.util.Arrays;
import java.util.List;

public class FileDeltaGridComponent<T>extends GridComponent<T> {
    private static final long serialVersionUID = -8895951052194750247L;
    private static final String ID = "FileDeltaGrid";
    public static final String HEADER = "File Deltas";
    private final static List<String> COLUMNS = Arrays.asList("fileName", "modificationType", "pathName");

    public FileDeltaGridComponent(final Class<T> fileDeltaClass) {
        super(fileDeltaClass,
              COLUMNS,
              ID);
    }
}
