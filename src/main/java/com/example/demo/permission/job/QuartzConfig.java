package com.example.demo.permission.job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Objects;

/**
 * @author alin
 */
@Configuration
public class QuartzConfig {

    /**
     * 1.创建Job对象
     */
    @Bean(name = "job1")
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        //关联我们自己的Job类
        factory.setJobClass(MyJob.class);
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
        factory.setCronExpression("0/2 * * * * ?");
        return factory;
    }

    /**
     * 3.创建Scheduler对象
     */
    @Bean(name = "sch")
    public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean, MyAdaptableJobFactory myAdaptableJobFactory) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTriggers(cronTriggerFactoryBean.getObject());
        factory.setJobFactory(myAdaptableJobFactory);
        return factory;
    }

}

