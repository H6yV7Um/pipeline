package com.jlu.plugin.dao.impl;

import org.springframework.stereotype.Repository;

import com.jlu.common.db.dao.AbstractBaseDao;
import com.jlu.plugin.dao.PluginInfoDao;
import com.jlu.plugin.model.PluginInfo;

/**
 * Created by langshiquan on 18/1/15.
 */
@Repository
public class PluginInfoDaoImpl extends AbstractBaseDao<PluginInfo> implements PluginInfoDao {

    @Override
    public PluginInfo findByJobType(String jobType) {
        return null;
    }
}