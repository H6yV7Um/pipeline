package com.jlu.pipeline.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.jlu.branch.bean.BranchType;
import com.jlu.common.db.dao.AbstractBaseDao;
import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.pipeline.dao.IPipelineConfDao;
import com.jlu.pipeline.model.PipelineConf;

/**
 * Created by langshiquan on 18/1/15.
 */
@Repository
public class PipelineConfDaoImpl extends AbstractBaseDao<PipelineConf> implements IPipelineConfDao {

    @Override
    public List<PipelineConf> get(String owner, String module) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("owner", owner);
        conditionAndSet.put("module", module);
        List<PipelineConf> pipelineConfs = findByProperties(conditionAndSet);
        return CollectionUtils.isEmpty(pipelineConfs) ? new ArrayList<>() : pipelineConfs;
    }

    @Override
    public PipelineConf get(String owner, String module, BranchType branchType) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("owner", owner);
        conditionAndSet.put("module", module);
        conditionAndSet.put("branchType", branchType);
        List<PipelineConf> pipelineConfs = findByProperties(conditionAndSet);
        return CollectionUtils.isEmpty(pipelineConfs) ? null : pipelineConfs.get(0);
    }
}
