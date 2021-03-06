package com.jlu.pipeline.job.dao;

import java.util.List;

import com.jlu.common.db.dao.IBaseDao;
import com.jlu.pipeline.job.model.JobConf;

/**
 * Created by langshiquan on 18/1/14.
 */
public interface IJobConfDao extends IBaseDao<JobConf> {
    List<JobConf> get(Long pipelineConfId, Boolean deleteStatus);
}
