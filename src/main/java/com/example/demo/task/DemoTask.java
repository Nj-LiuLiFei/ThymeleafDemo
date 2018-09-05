package com.example.demo.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.SimpleDateFormat;
import java.util.Date;


public class DemoTask implements Runnable{

    private int j=0;
    private static final Logger log = LoggerFactory.getLogger(DemoTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void run() {
        log.info("j="+j++);
        log.info("DemoTask  任务调度 {}", dateFormat.format(new Date()));
    }
}
