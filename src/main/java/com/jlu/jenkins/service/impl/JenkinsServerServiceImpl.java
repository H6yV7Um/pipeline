package com.jlu.jenkins.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jlu.jenkins.exception.JenkinsRuntimeException;
import com.jlu.jenkins.exception.JenkinsRuntimeExceptionEnum;
import com.jlu.jenkins.model.JenkinsConf;
import com.jlu.jenkins.service.JenkinsServerService;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;

/**
 * Created by langshiquan on 17/12/23.
 */

@Service
public class JenkinsServerServiceImpl implements JenkinsServerService {

    private Logger logger = LoggerFactory.getLogger(JenkinsServerServiceImpl.class);
    private ConcurrentHashMap<String, JenkinsServer> jenkinsServerMap = new ConcurrentHashMap<>();

    @Override
    public JenkinsServer getJenkinsServer(String serverUrl, String username, String password) {
        JenkinsServer jenkinsServer = jenkinsServerMap.get(serverUrl);
        if (jenkinsServer != null) {
            return jenkinsServer;
        }
        JenkinsServer newJenkinsServer = initJenkinsServer(serverUrl, username, password);
        jenkinsServerMap.put(serverUrl, newJenkinsServer);
        return newJenkinsServer;
    }

    @Override
    public JenkinsServer getJenkinsServer(JenkinsConf jenkinsConf) {
        String serverUrl = jenkinsConf.getServerUrl();
        String username = jenkinsConf.getMasterUser();
        String password = jenkinsConf.getMasterPassword();
        return getJenkinsServer(serverUrl, username, password);
    }

    @Override
    public Set<String> getJobs(JenkinsServer jenkinsServer) throws IOException {
        Map<String, Job> jobMap = null;

        jobMap = jenkinsServer.getJobs();

        if (jobMap == null) {
            return new HashSet<>();
        }
        return jobMap.keySet();
    }

    @Override
    public Boolean isExists(JenkinsServer jenkinsServer, String jobName) throws IOException {
        Set<String> jobs = getJobs(jenkinsServer);
        return jobs.contains(jobName);
    }

    @Override
    public Integer build(JenkinsServer jenkinsServer, String jobName, Map<String, String> params) throws IOException {
        Job job = jenkinsServer.getJob(jobName);
        QueueReference queueReference;
        try {
            queueReference = job.build(params, true);
        } catch (HttpResponseException e) {
            // log e.getMessage()
            queueReference = job.build(true);
        }
        QueueItem queueItem = jenkinsServer.getQueueItem(queueReference);
        String why = queueItem.getWhy();
        if (why != null && why.contains("offline")) {
            throw new JenkinsRuntimeException(JenkinsRuntimeExceptionEnum.SLAVE_OFFLINE);
        }
        Build build = jenkinsServer.getBuild(queueItem);
        return build.getNumber();
    }

    @Override
    public Long getLastSuccessfulBuildDuration(JenkinsServer jenkinsServer, String jobName) throws IOException {
        Job job = jenkinsServer.getJob(jobName);
        JobWithDetails jobWithDetails = job.details();
        Long duration = jobWithDetails.getLastSuccessfulBuild().details().getDuration();
        return duration;
    }

    @Override
    public Build getJobBuild(JenkinsServer jenkinsServer, String jobName, Integer buildNumber)
            throws IOException {
        Build build = jenkinsServer.getJob(jobName).details().getBuildByNumber(buildNumber);
        return build;
    }

    private JenkinsServer initJenkinsServer(String serverUrl, String username, String password) {
        URI uri;
        try {
            uri = new URI(serverUrl);
        } catch (URISyntaxException e) {
            throw new JenkinsRuntimeException(JenkinsRuntimeExceptionEnum.WRONG_URL);

        }
        JenkinsServer jenkinsServer = new JenkinsServer(uri, username, password);
        Boolean isRunning = jenkinsServer.isRunning();
        if (!isRunning) {
            throw new JenkinsRuntimeException(JenkinsRuntimeExceptionEnum.SERVER_INIT_FAILED);
        }
        return jenkinsServer;
    }

    @Override
    public Integer build(JenkinsServer jenkinsServer, String jobName) throws IOException {
        Job job = jenkinsServer.getJob(jobName);
        QueueReference queueReference = job.build();
        if (queueReference == null) {
            throw new JenkinsRuntimeException(JenkinsRuntimeExceptionEnum.SLAVE_OFFLINE);
        }
        QueueItem queueItem = jenkinsServer.getQueueItem(queueReference);
        Build build = jenkinsServer.getBuild(queueItem);
        return build.getNumber();
    }

    @Override
    public Integer buildWithParamters(JenkinsServer jenkinsServer, String jobName, Map<String, String> params)
            throws IOException {
        Job job = jenkinsServer.getJob(jobName);
        QueueReference queueReference = job.build(params, true);
        QueueItem queueItem = jenkinsServer.getQueueItem(queueReference);
        String why = queueItem.getWhy();
        if (why != null && why.contains("offline")) {
            throw new JenkinsRuntimeException(JenkinsRuntimeExceptionEnum.SLAVE_OFFLINE);
        }
        Build build = jenkinsServer.getBuild(queueItem);
        return build.getNumber();
    }

}