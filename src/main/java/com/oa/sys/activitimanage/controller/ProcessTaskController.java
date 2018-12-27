package com.oa.sys.activitimanage.controller;

import com.oa.sys.activitimanage.service.IWorkFlowService;
import com.oa.sys.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sys/processTask")
public class ProcessTaskController {
    private  static Logger logger= Logger.getLogger(ProcessTaskController.class);
//    @Autowired
//    private IWorkFlowService workFlowService;
//    public String gotoProcessTaskList(Model model){
//        List<Task> taskList=new ArrayList<>();
//        String taskList= UserUtils.getCurrentUserId().toString();
//        taskList=workFlowService.getTaskList(assingnee);
    }


