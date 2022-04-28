/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.process.dataimport.argus;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;

import nl.nfi.aardwolf.domain.Experiment;

public abstract class ArgusHelper {
    /**
     * Asserts the content of a set of experiments found during a test,
     * assuming they are extracted from the valid zip supplied in the resources folder
     * @param experiments
     */
    public static void testValidZipExperimentsOutput(final Collection<Experiment> experiments) {
        assertThat("experiments", experiments, not(nullValue()));
        assertThat("number of experiments", experiments.size(), equalTo(1));
        final Experiment experiment = experiments.toArray(new Experiment[1])[0];
        assertThat(experiment.getDateTimePerformed(), is(not(nullValue())));
        assertThat("date of experiment", experiment.getDateTimePerformed().toString(), equalTo("2020-11-23T13:30:26"));
        assertThat("operating system", experiment.getOperatingSystem(), equalTo("Android version 10"));
        assertThat("device details", experiment.getDeviceDetails(), equalTo("{\"device_type\":\"Android device\",\"device_name\":\"sdk_phone_x86_64\",\"product_brand\":\"Android\",\"product_model\":\"Android SDK built for x86_64\",\"serial\":\"emulator-5554\",\"sdk_version\":\"29\",\"is_emulator\":\"True\",\"OS_version\":\"10\"}"));
        assertThat("file changes", experiment.getChanges().size(), equalTo(9));
    }
}
