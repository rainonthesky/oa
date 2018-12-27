package com.oa.sys.activitimanage.controller;

import com.oa.sys.activitimanage.service.IWorkFlowService;
import org.activiti.engine.repository.Deployment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关的控制器转发
 */
@Controller
@RequestMapping("/sys/processDeploy")
public class ProcessDeployController {
    private static org.apache.log4j.Logger logger= Logger.getLogger(ProcessDeployController.class);
    @Autowired
    private IWorkFlowService workFlowService;
    @RequestMapping("/gotoProcessDeployList")
    public String gotoProcessDeployList(Model model){
        List<Deployment> deploymentList = new ArrayList<Deployment>();
        deploymentList = workFlowService.getDeployments();
        model.addAttribute("deploymentList",deploymentList);
        return "activitimanage/processDeploy/processDeployList";
    }
    @RequestMapping("/gotoProcessDeployAdd")
    public String gotoProcessDeployAdd(){
        return "activitimanage/processDeploy/processDeployAdd";
    }
    /**
     * 1.判断上传的文件是否为空
     * 2.判断上传的文件的后缀，给出提示
     * 3.调用接口完成流程的部署
     */
    @RequestMapping("/addProcessDeploy")
    public String addProcessDepoy(HttpServletRequest request, MultipartFile file,Model model){
        System.out.print(request.getParameter("name"));
        String deploymentName=request.getParameter("name");
        if(file!=null){
            String originalFileName=file.getOriginalFilename();
            if(originalFileName.indexOf("zip")>0){
                InputStream inputStream = null;
                try{
                    inputStream = file.getInputStream();
                    Deployment deploment = this.workFlowService.addDeployment(inputStream, deploymentName);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "redirect:/sys/processDeploy/gotoProcessDeployList";
            }else {
                model.addAttribute("processAddErrorMsg","流程部署压缩文件只能是ZIP格式");
                return  "activitimanage/processDeploy/processDeployAdd";
            }
        }else {
            model.addAttribute("processAddErrorMsg","请检查上传文件内容");
            return  "activitimanage/processDeploy/processDeployAdd";
        }
    }
    @RequestMapping("/delProcessDeploy")
    public @ResponseBody Map<String,Object>delProcessDeploy(String deploymentId){
        Map<String,Object>resultMap=new HashMap<>();
        try{
            this.workFlowService.delDeployment(deploymentId,true);
            resultMap.put("result","删除流程部署信息成功");
        }catch (Exception e){
            logger.error("删除流程部署信息失败",e);
            resultMap.put("result","删除流程部署失败");
        }
        return resultMap;
    }



}

