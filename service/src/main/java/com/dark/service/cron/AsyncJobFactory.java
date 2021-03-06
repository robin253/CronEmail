package com.dark.service.cron;

import com.dark.model.ScheduleJob;
import com.dark.service.base.MyApplicationContextUtil;
import com.dark.service.cron.util.ScheduleEmailExe;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.UUID;

/**
 * description : 异步
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class AsyncJobFactory extends QuartzJobBean {

    /* 日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(AsyncJobFactory.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("AsyncJobFactory execute");
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);
        ScheduleEmailExe scheduleExe = (ScheduleEmailExe) MyApplicationContextUtil.getContext().getBean("scheduleEmailExe");
        String uuid = UUID.randomUUID().toString();
        logger.info("====jobName:{},jobGroup:{},uuid:{}",new Object[]{scheduleJob.getJobName(),scheduleJob.getJobGroup(),uuid});
        scheduleExe.generateTemplateEmail(scheduleJob.getEcId(), uuid);
    }
}
