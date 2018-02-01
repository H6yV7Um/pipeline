package com.jlu.pipeline.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.jlu.common.db.dao.AbstractBaseDao;
import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.db.sqlcondition.DescOrder;
import com.jlu.common.db.sqlcondition.OrderCondition;
import com.jlu.common.utils.CollUtils;
import com.jlu.pipeline.dao.IPipelineBuildDao;
import com.jlu.pipeline.model.PipelineBuild;

/**
 * Created by langshiquan on 18/1/15.
 */
@Repository
public class PipelineBuildDaoImpl extends AbstractBaseDao<PipelineBuild> implements IPipelineBuildDao {

    @Override
    public Long getNextBuildNumber(String owner, String module) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("owner", owner);
        conditionAndSet.put("module", module);
        List<OrderCondition> orders = new ArrayList<OrderCondition>();
        orders.add(new DescOrder("buildNumber"));
        List<PipelineBuild> pipelineBuilds = findHeadByProperties(conditionAndSet, orders, 0, 1);
        return CollUtils.isEmpty(pipelineBuilds) ? 1L : pipelineBuilds.get(0).getBuildNumber() + 1L;
    }

    @Override
    public List<PipelineBuild> get(Long pipelineConfId) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("pipelineConfId", pipelineConfId);
        List<PipelineBuild> pipelineBuilds = findByProperties(conditionAndSet);
        return CollUtils.isEmpty(pipelineBuilds) ? new LinkedList<>() : pipelineBuilds;
    }
}
