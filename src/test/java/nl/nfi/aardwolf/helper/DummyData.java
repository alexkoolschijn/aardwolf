/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.helper;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.domain.FileDelta;
import nl.nfi.aardwolf.domain.ModificationType;

public class DummyData {

    public final static FileDelta CREATED_FILE = new FileDelta(ModificationType.CREATED, "createdtype", "createdpath");
    public final static FileDelta MODIFIED_FILE = new FileDelta(ModificationType.MODIFIED, "modifiedtype", "modifiedpath");
    public final static FileDelta DELETED_FILE = new FileDelta(ModificationType.DELETED, "deletedtype", "deletedpath");

    public final static App APP_1 = new App("name1", "publisher1", "version1", "description1");
    public final static App APP_2 = new App("name2", "publisher2", "version2", "description2");

    public final static Experiment EXPERIMENT_1 = new Experiment("user1", "device1", "os1", "description1", "method1", "script1");
    public final static Experiment EXPERIMENT_2 = new Experiment("user2", "device2", "os2", "description2", "method2", "script2");
}
