/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.mongo;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;

public class MongoPanelComponent extends VerticalLayout {
    private static final long serialVersionUID = -1348914079564398754L;

    private ComboBox<String> _comboBox;
    private RecordsPanel _uniqueRecords;
    private RecordsPanel _duplicateRecords;
    private RecordsPanel _totalRecords;
    private Checkbox _checkBox;
    private Button _connectButton;
    private Button _importButton;
    protected final static String COMBO_BOX_ID = "MongoConnectComboBox";
    protected final static String UNIQUE_RECORDS_ID = "UniqueRecordsPanel";
    protected final static String DUPLICATE_RECORDS_ID = "DuplicateRecordsPanel";
    protected final static String TOTAL_RECORDS_ID = "TotalRecordsPanel";
    protected final static String CONNECT_BUTTON_ID = "MongoConnectButton";
    protected final static String IMPORT_BUTTON_ID = "MongoImportButton";
    protected final static String CHECKBOX_ID = "RemoveEntriesCheckBox";

    public MongoPanelComponent() {
        addComponents();
        setSettings();
    }

    protected void addComponents() {
        add(createReadPanel());
        addRecords();
        add(createCheckbox());
        add(createImportButton());
    }

    protected void setSettings() {
        setAlignItems(Alignment.CENTER);
        setWidth(null);
    }

    protected HorizontalLayout createReadPanel() {
        HorizontalLayout readPanel = new HorizontalLayout(createComboBox(), createConnectButton());
        readPanel.setWidthFull();
        readPanel.setAlignItems(Alignment.END);
        return readPanel;
    }

    protected void addRecords() {
        _uniqueRecords = new RecordsPanel(0,"Unique records", UNIQUE_RECORDS_ID);
        _duplicateRecords = new RecordsPanel(0,"Duplicate records", DUPLICATE_RECORDS_ID);
        _totalRecords = new RecordsPanel(0,"Total records", TOTAL_RECORDS_ID);
        add(_uniqueRecords);
        add(_duplicateRecords);
        add(_totalRecords);
    }

    protected ComboBox<String> createComboBox() {
        _comboBox = new ComboBox<>("MongoDB Connection");
        _comboBox.setClearButtonVisible(true);
        _comboBox.setWidthFull();
        _comboBox.setItems(getMongoDBConnections());
        _comboBox.addCustomValueSetListener(event -> {
            _comboBox.setValue(event.getDetail());
        });
        _comboBox.setId(COMBO_BOX_ID);
        return _comboBox;
    }

    private List<String> getMongoDBConnections() {
        final String[] urls = findCookie("aardwolf-mongo-connections").getValue().split("\\|");
        return Arrays.asList(urls);
    }

    public void saveMongodbConnectString(final String url) {
        final Cookie mongodbUrls = findCookie("aardwolf-mongo-connections");

        String urls = mongodbUrls.getValue();
        if (!urls.matches("(^|\\|)" + url + "(\\||$)")) {
            if (!urls.isEmpty()) {
                urls += "|";
            }
            urls += url;

            mongodbUrls.setValue(urls);
            VaadinService.getCurrentResponse().addCookie(mongodbUrls);
        }
    }

    private Cookie findCookie(final String cookieName) {
        final Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        if (cookies != null) {
            for (final Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        final Cookie c = new Cookie(cookieName, "");
        c.setMaxAge(Integer.MAX_VALUE);
        return c;
    }

    protected Checkbox createCheckbox() {
        _checkBox = new Checkbox("Remove existing record before importing", true);
        _checkBox.setId(CHECKBOX_ID);
        return _checkBox;
    }

    protected Button createConnectButton() {
        _connectButton = new Button(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
        _connectButton.setId(CONNECT_BUTTON_ID);
        return _connectButton;
    }

    protected Button createImportButton() {
        _importButton = new Button("Import records", VaadinIcon.DATABASE.create());
        _importButton.setId(IMPORT_BUTTON_ID);
        _importButton.setEnabled(false);
        return _importButton;
    }

    public ComboBox<String> getComboBox(){
        return _comboBox;
    }

    public RecordsPanel getUniqueRecordsPanel() {
        return _uniqueRecords;
    }

    public RecordsPanel getDuplicateRecordsPanel() {
        return _duplicateRecords;
    }

    public RecordsPanel getTotalRecordsPanel() {
        return _totalRecords;
    }

    public Checkbox getCheckBox() {
        return _checkBox;
    }

    public Button getConnectButton() {
        return _connectButton;
    }

    public Button getImportButton() {
        return _importButton;
    }
}
