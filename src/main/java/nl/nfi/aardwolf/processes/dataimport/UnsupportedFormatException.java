/**
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.processes.dataimport;

import java.util.List;

/**
 * Thrown by the {@link ExperimentsReader} if none
 * of the registered reader implementations support the supplied format.
 */
public class UnsupportedFormatException extends Exception {
    private static final long serialVersionUID = 65202236242139577L;

    protected List<Exception> _exceptions;

    /**
     * Constructor
     *
     * @param supportedFormats a string containing the supported formats
     * @param exceptions a {@link List} of {@link Exception}s
     */
    public UnsupportedFormatException(final String supportedFormats, final List<Exception> exceptions) {
        super("The data was not in any supported format. Supported formats are: " + supportedFormats);
        _exceptions = exceptions;
    }

    public List<Exception> getExceptions() {
        return _exceptions;
    }

}
