package com.jlu.jenkins.service;

import com.jlu.jenkins.model.JenkinsConf;

/**
 * Created by langshiquan on 18/1/10.
 */
public interface IJenkinsConfService {
    void saveOrUpdate(JenkinsConf jenkinsConf);

    JenkinsConf get(Long id);
}
