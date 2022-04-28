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
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity
public class App {
    @Id
    @GeneratedValue
    public Long id;

    private String name;
    private String version;
    private String publisher;
    @Lob
    private String description;
    @Lob
    private String apkMetadata;
    private String ipaMetadata;
    private String installationFile;
    private Marketplace marketplace;

    @UpdateTimestamp
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime updateTimeStamp;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Experiment> experiments;

    public App() {
    }

    public App(final String newName, final String newPublisher, final String newVersion, final String newDescription) {
        name = newName;
        publisher = newPublisher;
        version = newVersion;
        setDescription(newDescription);
        marketplace = Marketplace.OTHER;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(final String publisher) {
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the apkMetadata
     */
    public String getApkMetadata() {
        return apkMetadata;
    }

    /**
     * @param apkMetadata the apkMetadata to set
     */
    public void setApkMetadata(final String apkMetadata) {
        this.apkMetadata = apkMetadata;
    }

    /**
     * @return the ipaMetadata
     */
    public String getIpaMetadata() {
        return ipaMetadata;
    }

    /**
     * @param ipaMetadata the ipaMetadata to set
     */
    public void setIpaMetadata(final String ipaMetadata) {
        this.ipaMetadata = ipaMetadata;
    }

    /**
     * @return the installationFile
     */
    public String getInstallationFile() {
        return installationFile;
    }

    /**
     * @param installationFile the installationFile to set
     */
    public void setInstallationFile(final String installationFile) {
        this.installationFile = installationFile;
    }

    /**
     * @return the updateTimeStamp
     */
    public LocalDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    /**
     * @return the experiments
     */
    public List<Experiment> getExperiments() {
        return experiments;
    }

    /**
     * @param experiments the experiments to set
     */
    public void setExperiments(final List<Experiment> experiments) {
        this.experiments = experiments;
    }

    /**
     * @return the marketplace
     */
    public Marketplace getMarketplace() {
        return marketplace;
    }

    /**
     * @param marketplace the marketplace to set
     */
    public void setMarketplace(final Marketplace marketplace) {
        this.marketplace = marketplace;
    }
}