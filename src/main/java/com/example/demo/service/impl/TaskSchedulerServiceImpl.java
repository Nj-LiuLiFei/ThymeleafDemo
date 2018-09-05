package com.example.demo.service.impl;

import com.example.demo.service.TaskSchedulerService;
import com.example.demo.task.ScheduledTasks;
import com.example.demo.task.ThreadPoolTaskSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledFuture;

@Service("taskSchedulerService")
public class TaskSchedulerServiceImpl implements TaskSchedulerService {

    private final static Logger logger = LoggerFactory.getLogger(TaskSchedulerServiceImpl.class);

    @Autowired
    @Qualifier("ThreadPoolTaskSchedulerFactory")
    private ThreadPoolTaskSchedulerFactory threadPoolTaskSchedulerFactory;

    @Override
    public void add(String className,String cronTrigger) {
        //new CronTrigger("0/5 * * * * ?")
        threadPoolTaskSchedulerFactory.addTask(className,cronTrigger);
    }

    @Override
    public void cance(String className,boolean mayInterruptIfRunning) {
        threadPoolTaskSchedulerFactory.cancelTask(className,mayInterruptIfRunning);
    }

    @Override
    public void update(String className, String cronTrigger,boolean mayInterruptIfRunning) {
        threadPoolTaskSchedulerFactory.updateTask(className,cronTrigger,mayInterruptIfRunning);
    }

}
