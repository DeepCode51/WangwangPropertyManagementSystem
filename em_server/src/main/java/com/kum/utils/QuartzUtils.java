package com.kum.utils;

import com.kum.domain.entity.SysJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @Package com.kum.utils
 * @auhter 枯木Kum
 * @date 2021/2/24-3:55 PM
 */

//@Component
public class QuartzUtils {
//    @Autowired
    private Scheduler scheduler = null;

    public boolean add(SysJob sysJob) {

        try {
            Class clazz = Class.forName(sysJob.getClassPath());
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(sysJob.getName(), sysJob.getGroupName()).build();
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCron());
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(sysJob.getName(), sysJob.getGroupName())
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
            // 如果当前添加的任务状态为「暂停」,则无需启动此任务。
            if(sysJob.getStatus().equals("1")){
                stop(sysJob.getName(),sysJob.getGroupName());
            }

            return true;
        } catch (SchedulerException | ClassNotFoundException e) {
            return false;
        }
    }

    public boolean stop(String name, String group) {
        try {
            scheduler.pauseJob(new JobKey(name, group));
            return true;
        } catch (SchedulerException e) {
            return false;
        }
    }

    public boolean resume(String name, String group) {
        try {
            scheduler.resumeJob(new JobKey(name, group));
            return true;
        } catch (SchedulerException e) {
            return false;
        }
    }

    public boolean delete(String name, String group) {
        try {
            scheduler.deleteJob(new JobKey(name, group));
            return true;
        } catch (SchedulerException e) {
            return false;
        }
    }

    public boolean isExists(String name, String group) {

        try {
            return scheduler.checkExists(new JobKey(name, group));
        } catch (SchedulerException e) {
            return false;
        }
    }

    public boolean update(SysJob sysJob) {
        boolean b = isExists(sysJob.getName(), sysJob.getGroupName());
        if (b) {
            delete(sysJob.getName(), sysJob.getGroupName());
        }
        return add(sysJob);
    }

}
