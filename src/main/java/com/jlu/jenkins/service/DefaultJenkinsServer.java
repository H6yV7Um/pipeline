package com.jlu.jenkins.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jlu.jenkins.model.JenkinsConf;

/**
 * Created by langshiquan on 18/1/21.
 */
@Component
public class DefaultJenkinsServer {
    public static Long ID;
    public final static String SERVER_URL = "http://60.205.178.34:8081/";
    public final static String MASTER_USERNAME = "langshiquan";
    public final static String MASTER_PASSWORD = "941207";
    public final static String ADMIN_USERNAME = "pipeline_admin";

    @Autowired
    private IJenkinsConfService jenkinsConfService;

    @PostConstruct
    public void initCompileJenkinsServer() {
        JenkinsConf jf = jenkinsConfService.get(DefaultJenkinsServer.SERVER_URL,
                DefaultJenkinsServer.MASTER_USERNAME, DefaultJenkinsServer.MASTER_PASSWORD);
        if (jf != null) {
            ID = jf.getId();
            return;
        }
        JenkinsConf jenkinsConf = new JenkinsConf();
        jenkinsConf.setDeleteStatus(false);
        jenkinsConf.setServerUrl(DefaultJenkinsServer.SERVER_URL);
        jenkinsConf.setMasterUser(DefaultJenkinsServer.MASTER_USERNAME);
        jenkinsConf.setMasterPassword(DefaultJenkinsServer.MASTER_PASSWORD);
        jenkinsConf.setCreateTime(new Date());
        jenkinsConf.setLastModifiedTime(new Date());
        jenkinsConf.setCreateUser(DefaultJenkinsServer.ADMIN_USERNAME);
        jenkinsConf.setLastModifiedUser(DefaultJenkinsServer.ADMIN_USERNAME);
        jenkinsConfService.saveOrUpdate(jenkinsConf);
        ID = jenkinsConf.getId();
    }
}