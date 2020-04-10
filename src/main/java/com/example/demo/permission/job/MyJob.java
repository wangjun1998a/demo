package com.example.demo.permission.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author alin
 */
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        //自定义execute
//        System.out.println("测试定时任务！！");
        System.out.println(date);
    }
}
