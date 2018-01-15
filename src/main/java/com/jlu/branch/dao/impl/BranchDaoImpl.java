package com.jlu.branch.dao.impl;

import org.springframework.stereotype.Repository;

import com.jlu.branch.dao.IBranchDao;
import com.jlu.branch.model.CiHomeBranch;
import com.jlu.common.db.dao.AbstractBaseDao;

/**
 * Created by niuwanpeng on 17/3/10.
 *
 *  模块信息dao实体类
 */
@Repository
public class BranchDaoImpl extends AbstractBaseDao<CiHomeBranch> implements IBranchDao {
}
