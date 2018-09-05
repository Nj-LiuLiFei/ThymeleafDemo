package com.example.demo.service;

public interface TaskSchedulerService {
    void add(String className,String cronTrigger);
    void cance(String className,boolean mayInterruptIfRunning);
    void update(String className,String cronTrigger,boolean mayInterruptIfRunning);
}
