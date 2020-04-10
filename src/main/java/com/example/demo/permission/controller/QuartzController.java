package com.example.demo.permission.controller;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author alin
 */
@Controller
@RequestMapping("/quartz")

public class QuartzController {

    @Autowired
    @Qualifier("sch")
    private Scheduler scheduler;

    @RequestMapping("/stop")
    public void pause(Scheduler scheduler) {
        System.out.println("Job Stopped...");
        System.out.println(scheduler);

        JobKey key = new JobKey("job1");
        try {
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/start")
    public void start(Scheduler scheduler) {
        System.out.println("Job Starting...");
        System.out.println(scheduler);

        JobKey key = new JobKey("job1");
        try {
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
