package com.example.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScheduledTasks implements Runnable{
    private int i=0;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");



    @Override
    public void run() {
        log.info("i="+i++);
        log.info("ScheduledTasks  任务调度 {}", dateFormat.format(new Date()));
    }
}
