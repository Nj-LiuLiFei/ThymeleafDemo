package com.example.demo.controller;

import com.example.demo.service.TaskSchedulerService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("TaskScheduler")
public class TaskSchedulerController {

    @Autowired
    @Qualifier("taskSchedulerService")
    private TaskSchedulerService taskSchedulerService;

    @ResponseBody
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public ResponseEntity addTaskScheduler(@RequestParam(name="className",required = true) String className, @RequestParam(name="cronTrigger",required = true) String cronTrigger){
        taskSchedulerService.add(className,cronTrigger);
        return  new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "cance",method = RequestMethod.POST)
    public ResponseEntity canceTaskScheduler(@RequestParam(name="className",required = true) String className,
                                             @RequestParam(name="mayInterruptIfRunning",required = true) boolean mayInterruptIfRunning){

        taskSchedulerService.cance(className,mayInterruptIfRunning);
        return  new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public ResponseEntity updateTaskScheduler(@RequestParam(name="className",required = true) String className,
                                              @RequestParam(name="cronTrigger",required = true) String cronTrigger,
                                              @RequestParam(name="mayInterruptIfRunning",required = true) boolean mayInterruptIfRunning){
        taskSchedulerService.update(className,cronTrigger,mayInterruptIfRunning);
        return  new ResponseEntity(HttpStatus.OK);
    }


}
