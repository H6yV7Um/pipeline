package com.jlu.plugin.instance.compile.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by langshiquan on 18/1/20.
 */
@Entity
public class CompileBuild {
    private static final String WGET_COMMAND_PREFIX = "wget -r -nH --level=0 --cut-dirs=6 ";
    private static final String WGET_COMMAND_SUFFIX =
            "/output --user getprod --password getprod@123 --preserve-permissions";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isMulti = true;

    public Boolean getMulti() {
        if (isMulti == null) {
            return false;
        }
        return isMulti;
    }

    public void setMulti(Boolean multi) {
        if (isMulti == null) {
            isMulti = false;
        }
        isMulti = multi;
    }

    private String logUrl;

    private String buildPath;

    private String message;

    private Integer buildNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getBuildPath() {
        return buildPath;
    }

    public void setBuildPath(String buildPath) {
        this.buildPath = buildPath;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductWgetCommand() {
        return WGET_COMMAND_PREFIX + buildPath + WGET_COMMAND_SUFFIX;
    }
}
