package com.oa.sys.activitimanage.controller;
import com.oa.sys.activitimanage.service.IWorkFlowService;
import org.springframework.ui.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sys/processDefinition")
public class ProcessDefinitionController {
    private static Logger logger=Logger.getLogger(ProcessDefinitionController.class);
    @Autowired
    private IWorkFlowService workFlowService;

    /**
     * 进入流程定义页面
     * @param model
     * @return
     */

    @RequestMapping("/gotoProcessDefinitionList")
    public String gotoProcessDefinitionList(Model model){

        List<ProcessDefinition> processDefinitionList=new ArrayList<>();
        processDefinitionList =workFlowService.getProcessDefinitions();
        model.addAttribute("processDefinitionList",processDefinitionList);
        return "activitimanage/processDefinition/processDefinitionList";
    }

    @RequestMapping("/gotoProcessDefinitionImage")
    public String gotoProcessDefinitionImage(@ModelAttribute("deploymentId") String deploymentId,
                                             @ModelAttribute("imageName")String imageName){
        return "activitimanage/processDefinition/processDefinitionImage";

    }
    @RequestMapping("/getProcessDefinitionImage")

    public void getProcessDefinitionImage(String deploymentId, String imageName, HttpServletResponse response){
        InputStream inputStream=workFlowService.getProcessDefinitionImageStream(deploymentId,imageName);
        response.setContentType("img/png");
        response.setCharacterEncoding("UTF-8");
        try{
            OutputStream outputStream=response.getOutputStream();
            int len = 0;
            byte[] buffer=new byte[1024];
            while((len=inputStream.read(buffer,0,1024))!=-1){
                outputStream.write(buffer,0,len);
            }
            outputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
