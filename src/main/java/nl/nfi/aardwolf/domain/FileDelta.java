/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.domain;

import java.nio.file.Paths;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.io.FilenameUtils;

@Entity
public class FileDelta {
    @Id
    @GeneratedValue
    private Long _id;

    private String _fileContent;
    protected String _pathName;
    protected String _fileName;
    protected String _fileType;
    protected ModificationType _modificationType;

    public FileDelta() {
    }

    public FileDelta(final ModificationType modificationType, final String fileType, final String pathName) {
        this(Paths.get(pathName).getFileName().toString(), fileType, modificationType, pathName);
    };

    public FileDelta(final FileDelta fileDelta) {
        this(fileDelta.getFileName(),
             fileDelta.getFileType(),
             fileDelta.getModificationType(),
             fileDelta.getPathName());
    }

    private FileDelta(final String fileName, final String fileType, final ModificationType modificationType, final String pathName) {
        _fileName = fileName;
        _modificationType = modificationType;
        _fileType = fileType;
        _pathName = pathName;
    }

    /**
     * @return the modificationType
     */
    public ModificationType getModificationType() {
        return _modificationType;
    }

    /**
     * @param modificationType the modificationType to set
     */
    public void setModificationType(final ModificationType modificationType) {
        _modificationType = modificationType;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return _fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(final String fileType) {
        _fileType = fileType;
    }

    /**
     * @return the pathName
     */
    public String getPathName() {
        return _pathName;
    }

    /**
     * @param pathName the pathName to set
     */
    public void setPathName(final String pathName) {
        _pathName = pathName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return FilenameUtils.getName(_pathName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_fileContent, _fileName, _fileType, _modificationType, _pathName);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof FileDelta)) {
            return false;
        }
        final FileDelta other = (FileDelta) obj;
        return other.getFileName().equals(getFileName()) && other.getFileType().equals(getFileType()) && other.getModificationType().equals(getModificationType()) && other.getPathName().equals(getPathName());
    }
}
