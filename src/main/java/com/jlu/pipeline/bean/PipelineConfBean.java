package com.jlu.pipeline.bean;

import java.util.List;

import com.jlu.pipeline.job.bean.JobConfBean;
import com.jlu.pipeline.model.PipelineConf;

/**
 * Created by langshiquan on 18/1/14.
 */
public class PipelineConfBean extends PipelineConf {

    private List<JobConfBean> jobConfs;

    public List<JobConfBean> getJobConfs() {
        return jobConfs;
    }

    public void setJobConfs(List<JobConfBean> jobConfs) {
        this.jobConfs = jobConfs;
    }
}