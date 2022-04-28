/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Marketplace;
import nl.nfi.aardwolf.repositories.AppRepository;
import nl.nfi.aardwolf.ui.component.grid.AppGridComponent;
import nl.nfi.aardwolf.ui.component.mongo.MongoPanelComponent;

@PageTitle("Aardwolf | Import")
@Route(value = "import", layout = MainView.class)
public class ImportView extends VerticalLayout {
    private static final long serialVersionUID = -582093864028395227L;

    @Autowired
    private AppRepository _appRepo;

    private final List<App> _newApps = new ArrayList<>();
    private boolean dropExistingRecords = Boolean.TRUE;
    int _totalRecords;
    int _duplicateRecords;

    private final MongoPanelComponent _controls = new MongoPanelComponent();
    private final Grid<App> _appGrid;

    public ImportView() {
        _appGrid = createApplicationGrid();
        addMongoPanelComponentListeners();

        final HorizontalLayout content = new HorizontalLayout(_controls, _appGrid);
        content.setWidthFull();
        content.setHeightFull();

        final VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();
        layout.setHeightFull();
        layout.add(new H1("Data Import"));
        layout.add(new Text(
                            "On this screen you can import new application data into the database by supplying the connection to a MongoDB database."));
        layout.add(content);

        add(layout);
        setHeightFull();
    }

    private void addMongoPanelComponentListeners() {
        _controls.getConnectButton().addClickListener(event -> {
            final String mongo = _controls.getComboBox().getValue();
            if (!mongo.isEmpty()) {
                _controls.getComboBox().setEnabled(false);
                _controls.getCheckBox().setEnabled(false);
                _controls.getImportButton().setEnabled(false);
                try (final MongoClient client = MongoClients.create(mongo)) {
                    _duplicateRecords = 0;
                    _totalRecords = 0;
                    readPlaystoreApps(client);
                    readAppStoreApps(client);
                    _controls.saveMongodbConnectString(mongo);
                    _appGrid.setItems(_newApps);
                    _controls.getTotalRecordsPanel().setRecords(_totalRecords);
                    _controls.getDuplicateRecordsPanel().setRecords(_duplicateRecords);
                    _controls.getUniqueRecordsPanel().setRecords(_newApps.size());
                    _controls.getImportButton().setEnabled(_newApps.size() > 0);
                }
                catch (final Throwable e) {
                    final Dialog errorDialog = new Dialog();
                    final Button button = new Button("OK");
                    button.addClickListener(click -> errorDialog.close());
                    errorDialog.add(new Text(e.getMessage()), button);
                    errorDialog.open();
                }
                finally {
                    _controls.getComboBox().setEnabled(true);
                    _controls.getCheckBox().setEnabled(true);
                }
            }
        });

        _controls.getImportButton().addClickListener(event -> {
            dropExistingRecords = _controls.getCheckBox().getValue();
            final Notification notification = Notification.show("Importing records", 0, Notification.Position.MIDDLE);
            if (dropExistingRecords) {
                notification.setText("Deleting existing records");
                _appRepo.deleteAll();
            }
            notification.setText("Importing new records");
            _appRepo.saveAll(_newApps);
            notification.close();
            Notification.show("Successfully imported " + _newApps.size() + " records", 2000, Notification.Position.MIDDLE);
            _newApps.clear();
            _appGrid.setItems(_newApps);
            _controls.getImportButton().setEnabled(false);
        });
    }

    private void readAppStoreApps(final MongoClient client) {
        final MongoCollection<Document> appstorePosts = client.getDatabase("metadata_appstore").getCollection("posts");
        for (final Document document : appstorePosts.find()) {
            final String name = document.getString("trackName");
            final String version = document.getString("version");
            final String developer = document.getString("sellerName");
            final App app = new App(name, developer, version, document.getString("description"));
            app.setMarketplace(Marketplace.APPSTORE);
            _totalRecords++;
            if (_newApps.stream().noneMatch(existingApp -> existingApp.getName().equalsIgnoreCase(app.getName()) && existingApp.getVersion().equalsIgnoreCase(version) && existingApp.getMarketplace() == app.getMarketplace()) && !_appRepo.existsByNameAndVersionAndMarketplace(name, version, Marketplace.APPSTORE)) {
                _newApps.add(app);
            }
            else {
                _duplicateRecords++;
            }
        }
    }

    private void readPlaystoreApps(final MongoClient client) {
        try {
            final MongoCollection<Document> playstorePosts = client.getDatabase("metadata_googleplay").getCollection("posts");
            final MongoCollection<Document> apkfiles = client.getDatabase("apk_googleplay").getCollection("fs.files");
            for (final Document document : playstorePosts.find()) {
                final String name = document.getString("title");
                final String version = document.getString("current_version");
                final String developer = document.getString("developer");
                final App app = new App(name, developer, version, document.getString("description"));
                app.setMarketplace(Marketplace.PLAYSTORE);
                _totalRecords++;
                if (_newApps.stream().noneMatch(
                                                existingApp -> existingApp.getName().equalsIgnoreCase(app.getName()) && existingApp.getVersion().equalsIgnoreCase(version) && existingApp.getMarketplace() == app.getMarketplace()) && !_appRepo.existsByNameAndVersionAndMarketplace(name, version, Marketplace.PLAYSTORE)) {
                    final FindIterable<Document> apkFileMeta = apkfiles.find(and(eq("appId", document.getString("app_id")), eq("version", document.getString("current_version"))));
                    final Document apk = apkFileMeta.first();
                    if (apk != null) {
                        app.setApkMetadata(apk.toJson());
                    }
                    _newApps.add(app);
                }
                else {
                    _duplicateRecords++;
                }
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private Grid<App> createApplicationGrid() {
        final AppGridComponent appGrid = new AppGridComponent();
        appGrid.setWidthFull();

        final ListDataProvider<App> dataProvider = new ListDataProvider<>(_newApps);

        // Todo: Note: setDataProvider is deprecated from Vaadin 17 onward.
        appGrid.setDataProvider(dataProvider);
        return appGrid;
    }
}
