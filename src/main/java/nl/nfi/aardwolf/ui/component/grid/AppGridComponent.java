/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.grid;

import java.util.Arrays;
import java.util.List;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.ui.component.RecordDetail;

public class AppGridComponent extends GridComponent<App> {
    private static final long serialVersionUID = 4092606994815414436L;
    public static final String ID = "AppGrid";

    protected static String APP_KEY = "name";
    protected static String VERSION_KEY = "version";
    protected static String PUBLISH_KEY = "publisher";
    protected static String MARKETPLACE_KEY = "marketplace";

    public static final String HEADER = "Applications";
    private static List<String> SIMPLE_COLS = Arrays.asList(new String[]{APP_KEY, VERSION_KEY, PUBLISH_KEY, MARKETPLACE_KEY});

    @Override
    protected List<RecordDetail> descriptions(final App app) {
        return Arrays.asList(
                             new RecordDetail("Installation File", app.getInstallationFile()),
                             new RecordDetail("APK Metadata", app.getApkMetadata()),
                             new RecordDetail("IPA Metadata", app.getIpaMetadata()),
                             new RecordDetail("Description", app.getDescription()));
    }

    public AppGridComponent() {
        super(App.class, SIMPLE_COLS, ID);
    }

    public AppGridComponent(final List<String> simpleCols, final List<String> orderedCols) {
        super(App.class, simpleCols, orderedCols, ID); // Provide explicit bean type
    }
}
