package com.example.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component("ThreadPoolTaskSchedulerFactory")
public class ThreadPoolTaskSchedulerFactory extends ThreadPoolTaskScheduler{

    private final static Logger logger = LoggerFactory.getLogger(ThreadPoolTaskSchedulerFactory.class);

    //核心线程数
    private final int CORE_POOL_SIZE = 3;
    //正在运行的任务队列
    private static Map taskQueueMap = Collections.synchronizedMap(new HashMap<String, ScheduledFuture>());


    public ThreadPoolTaskSchedulerFactory(){
        super.setPoolSize(CORE_POOL_SIZE);
    }
    public void addTask(String className,String cronTrigger){
        try {

            ScheduledFuture scheduledFuture = (ScheduledFuture) taskQueueMap.get(className);
            if(scheduledFuture == null){
                logger.info("任务添加成功："+className);
                Class<?> runnable= ClassUtils.forName(className,Runnable.class.getClassLoader());
                scheduledFuture = super.schedule((Runnable) runnable.newInstance(),new CronTrigger(cronTrigger));
                taskQueueMap.put(className,scheduledFuture);
            }else {
                logger.error("相同的任务不能添加："+className);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
    public void cancelTask(String className,boolean mayInterruptIfRunning){
        ScheduledFuture scheduledFuture = (ScheduledFuture) taskQueueMap.get(className);
        if(scheduledFuture == null) {
            logger.error("取消失败：找不到 {} 任务",className);
        }
        logger.info("当前的任务队列：{}",super.getScheduledThreadPoolExecutor().getPoolSize());
        scheduledFuture.cancel(mayInterruptIfRunning);
        //taskQueueMap.remove(className);
        logger.info("取消之后的的任务队列：{}",super.getScheduledThreadPoolExecutor().getCompletedTaskCount());
    }
    public void activeTask(String className){
        ScheduledFuture scheduledFuture = (ScheduledFuture) taskQueueMap.get(className);
        if(scheduledFuture == null){

        }
    }
    public void updateTask(String className,String cronTrigger,boolean mayInterruptIfRunning){
        this.cancelTask(className,mayInterruptIfRunning);
        this.addTask(className,cronTrigger);
        logger.info("更新成功！{}",className);
    }
}
