package com.jlu.plugin;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jlu.common.aop.annotations.LogExecTime;
import com.jlu.pipeline.job.bean.PipelineJobStatus;
import com.jlu.pipeline.job.model.JobBuild;
import com.jlu.pipeline.job.service.IJobBuildService;
import com.jlu.plugin.bean.JobBuildContext;
import com.jlu.plugin.runtime.RuntimeRequire;
import com.jlu.plugin.service.IPluginInfoService;

/**
 * Job executor
 */
public abstract class AbstractExecutor {

    @Autowired
    private IPluginInfoService pluginInfoService;
    @Autowired
    protected IJobBuildService jobBuildService;

    @LogExecTime
    public void executeJob(JobBuildContext context, JobBuild jobBuild) {
        if (jobBuild == null) {
            notifyJobStartFailed(jobBuild, "未找到Job构建记录");
            return;
        }
        Long pluginBuildId = jobBuild.getPluginBuildId();
        if (pluginBuildId == null || pluginBuildId == -1L || pluginBuildId == 0L) {
            notifyJobStartFailed(jobBuild, "未找到插件配置");
            return;
        }
        // 插件运行时的参数保存到PluginBuild中
        Map<String, Object> runtimePluginParam = context.getRuntimePluginParam();
        AbstractDataOperator abstractDataOperator = pluginInfoService.getRealJobPlugin(jobBuild.getPluginType()).getDataOperator();
        Object pluginBuild = abstractDataOperator.getBuild(jobBuild.getPluginBuildId());
        if (pluginBuild == null) {
            notifyJobStartFailed(jobBuild, "插件初始化失败");
            return;
        }
        Class buildClass = abstractDataOperator.getBuildClass();
        Field[] fields = buildClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field filed = fields[i];
            RuntimeRequire runtimeRequire = filed.getAnnotation(RuntimeRequire.class);
            if (runtimeRequire != null) {
                try {
                    filed.setAccessible(true);
                    filed.set(pluginBuild, runtimePluginParam.get(filed.getName()));
                } catch (Exception e) {
                    // TODO log
                    continue;
                }
            }
        }
        pluginInfoService.getRealJobPlugin(jobBuild.getPluginType()).getDataOperator().updateBuild(pluginBuild);
        execute(context, jobBuild);
    }

    protected abstract void execute(JobBuildContext context, JobBuild jobBuild);

    public void handleCallback(JobBuild jobBuild) {
        jobBuildService.notifiedJobBuildUpdated(jobBuild, new HashMap());
    }

    protected void notifyJobStartFailed(JobBuild jobBuild, String message) {
        jobBuild.setJobStatus(PipelineJobStatus.FAILED);
        jobBuild.setStartTime(new Date());
        jobBuild.setMessage(message);
        jobBuildService.saveOrUpdate(jobBuild);
    }

    protected void notifyJobStartSucc(JobBuild jobBuild) {
        jobBuild.setJobStatus(PipelineJobStatus.RUNNING);
        jobBuild.setStartTime(new Date());
        jobBuild.setEndTime(new Date());
        jobBuildService.saveOrUpdate(jobBuild);
    }

}
