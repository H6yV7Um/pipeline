package com.jlu.pipeline.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jlu.pipeline.job.bean.PipelineJobStatus;

/**
 * Created by langshiquan on 18/1/10.
 */
@Entity
public class PipelineBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Long pipelineConfId;
    protected String module;
    protected String branch;
    protected String commitId;
    protected Long triggerId;
    protected Long buildNumber;
    @Enumerated(EnumType.STRING)
    protected PipelineJobStatus pipelineJobStatus;
    protected String checkinAuthor;
    protected String triggerUser;
    protected String triggerMode;
    protected Date startTime;
    protected Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(Long triggerId) {
        this.triggerId = triggerId;
    }

    public Long getPipelineConfId() {
        return pipelineConfId;
    }

    public void setPipelineConfId(Long pipelineConfId) {
        this.pipelineConfId = pipelineConfId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public Long getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Long buildNumber) {
        this.buildNumber = buildNumber;
    }

    public PipelineJobStatus getPipelineJobStatus() {
        return pipelineJobStatus;
    }

    public void setPipelineJobStatus(PipelineJobStatus pipelineJobStatus) {
        this.pipelineJobStatus = pipelineJobStatus;
    }

    public String getCheckinAuthor() {
        return checkinAuthor;
    }

    public void setCheckinAuthor(String checkinAuthor) {
        this.checkinAuthor = checkinAuthor;
    }

    public String getTriggerUser() {
        return triggerUser;
    }

    public void setTriggerUser(String triggerUser) {
        this.triggerUser = triggerUser;
    }

    public String getTriggerMode() {
        return triggerMode;
    }

    public void setTriggerMode(String triggerMode) {
        this.triggerMode = triggerMode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
