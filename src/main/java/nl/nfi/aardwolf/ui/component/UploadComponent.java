/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component;

import java.util.Collection;
import java.util.Optional;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import elemental.json.Json;
import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Experiment;
import nl.nfi.aardwolf.processes.dataimport.ExperimentsReader;
import nl.nfi.aardwolf.repositories.AppRepository;
import nl.nfi.aardwolf.ui.appsearchview.AppSearchViewGridManager;
import nl.nfi.aardwolf.ui.component.dialog.ExceptionDialog;


public class UploadComponent extends Upload {
    private static final long serialVersionUID = -1710898754308566121L;
    private final static MemoryBuffer BUFFER = new MemoryBuffer();

    public UploadComponent(final AppSearchViewGridManager grids, final AppRepository appRepo) {
        super(BUFFER);
        init();
        addListeners(grids, appRepo);
    }

    private void init() {
        setVisible(false);
        setUploadButton(new Button("Upload experiment data"));
        setAcceptedFileTypes("application/x-compressed", "application/x-ms-wmz", "application/x-zip-compressed", "application/zip");
        setDropAllowed(false);
    }

    private void addListeners(final AppSearchViewGridManager grids, final AppRepository appRepo) {
        addSucceededListener(event -> {
            try {
                final Optional<App> appOpt = grids.getAppGrid().getSelectedItems().stream().findFirst();
                if (appOpt.isPresent()) {
                    final App app = appOpt.get();

                    Notification.show("Uploading experiment data");
                    final Collection<Experiment> experiments = ExperimentsReader
                        .readExperiments(
                                         event.getMIMEType(),
                                         event.getFileName(),
                                         BUFFER.getInputStream());

                    app.getExperiments().addAll(experiments);
                    appRepo.save(app);
                    grids.updateExperimentsGrid(app, this);
                    Notification.show("Uploaded " + event.getFileName());
                }
            }
            catch (final Exception e) {
                new ExceptionDialog("Error while processing " + event.getFileName() + ":<br>" + e.getMessage()).open();
            }
            finally {
                getElement().setPropertyJson("files", Json.createArray());
            }
        });

        addFailedListener(event -> {
            new ExceptionDialog("Failed to upload: " + event.getReason().getMessage()).open();
        });

        addFileRejectedListener(event -> {
            new ExceptionDialog("Upload was rejected: " + event.getErrorMessage()).open();
        });
    }
}
