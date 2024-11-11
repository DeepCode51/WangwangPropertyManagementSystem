package com.kum.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @version V1.0
 * @Package com.kum.quartz.task
 * @auhter 枯木Kum
 * @date 2021/2/24-10:37 AM
 */
public class MysqlBackupsJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String dbName = "estate_management";//备份的数据库名
        String username = "estate_management";//用户名
        String password = "123456";//密码
        String cmd = "mysqldump -u root -p123456 estate_management -r /Volumes/data/temp/a.sql";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            System.out.println("备份数据库成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
