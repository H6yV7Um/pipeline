package com.jlu.common.utils;

import org.apache.commons.lang.StringUtils;

import com.jlu.common.exception.PipelineRuntimeException;

/**
 * Created by langshiquan on 18/1/31.
 */
public class ModuleUtils {
    public static String getFullModule(String owner, String repository) {
        if (StringUtils.isBlank(owner) || StringUtils.isBlank(repository)) {
            throw new PipelineRuntimeException("模块名字不合法");
        }
        return owner + "/" + repository;
    }
}
