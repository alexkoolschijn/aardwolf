/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Experiment {
    @Id
    @GeneratedValue
    private Long _id;

    @NotNull
    protected LocalDateTime _dateTimePerformed;

    protected String _userName;
    protected String _deviceDetails;
    protected String _operatingSystem;
    protected String _description;
    protected String _methodDetails;
    protected String _script;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<FileDelta> _changes;

    public Experiment() {

    }

    public Experiment(final Experiment experiment) {
        this(
             experiment.getDateTimePerformed(),
             experiment.getUserName(),
             experiment.getDeviceDetails(),
             experiment.getOperatingSystem(),
             experiment.getDescription(),
             experiment.getMethodDetails(),
             experiment.getScript());
    }

    private Experiment(final LocalDateTime dateTimePerformed, final String userName, final String deviceDetails, final String operatingSystem, final String description, final String methodDetails, final String script) {
        _dateTimePerformed = dateTimePerformed;
        _userName = userName;
        _deviceDetails = deviceDetails;
        _operatingSystem = operatingSystem;
        _description = description;
        _methodDetails = methodDetails;
        _script = script;
    }

    public Experiment(final String userName, final String deviceDetails, final String operatingSystem, final String description, final String methodDetails, final String script) {
        this(LocalDateTime.now(), userName, deviceDetails, operatingSystem, description, methodDetails, script);
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return _userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(final String userName) {
        _userName = userName;
    }

    /**
     * @return the deviceDetails
     */
    public String getDeviceDetails() {
        return _deviceDetails;
    }

    /**
     * @param deviceDetails the deviceDetails to set
     */
    public void setDeviceDetails(final String deviceDetails) {
        _deviceDetails = deviceDetails;
    }

    /**
     * @return the operatingSystem
     */
    public String getOperatingSystem() {
        return _operatingSystem;
    }

    /**
     * @param operatingSystem the operatingSystem to set
     */
    public void setOperatingSystem(final String operatingSystem) {
        _operatingSystem = operatingSystem;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return _description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        _description = description;
    }

    /**
     * @return the methodDetails
     */
    public String getMethodDetails() {
        return _methodDetails;
    }

    /**
     * @param methodDetails the methodDetails to set
     */
    public void setMethodDetails(final String methodDetails) {
        _methodDetails = methodDetails;
    }

    /**
     * @return the script
     */
    public String getScript() {
        return _script;
    }

    /**
     * @param script the script to set
     */
    public void setScript(final String script) {
        _script = script;
    }

    /**
     * @return the changes
     */
    public List<FileDelta> getChanges() {
        return _changes;
    }

    /**
     * @param changes the changes to set
     */
    public void setChanges(final List<FileDelta> changes) {
        _changes = changes;
    }

    /**
     * @return the dateTimePerformed
     */
    public LocalDateTime getDateTimePerformed() {
        return _dateTimePerformed;
    }

    /**
     * @param dateTimePerformed the dateTimePerformed to set
     */
    public void setDateTimePerformed(final LocalDateTime dateTimePerformed) {
        _dateTimePerformed = dateTimePerformed;
    }
}
