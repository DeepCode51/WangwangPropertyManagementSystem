package com.kum.quartz.task;

import com.kum.utils.MailUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version V1.0
 * @Package com.kum.quartz.task
 * @auhter 枯木Kum
 * @date 2021/4/12-5:13 PM
 */
//public class MailGreet implements Job {
//
//    @Autowired
//    private MailUtils mailUtils;
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        mailUtils.sendMail("481326635@qq.com","早上好","今天应该是个好天气");
//    }
//}
