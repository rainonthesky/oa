package com.oa.sys.activitimanage.controller;

import com.oa.sys.activitimanage.service.ILeaveBeanService;
import com.oa.sys.activitimanage.service.IWorkFlowService;
import com.oa.sys.entity.LeaveBean;
import com.oa.sys.util.UserUtils;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/sys/leaveProcess")
public class LeaveProcessController {
    private static Logger logger=Logger.getLogger(LeaveProcessController.class);
    @Autowired
    private IWorkFlowService workFlowService;

    @Autowired
    private ILeaveBeanService leaveBeanService;
    @RequestMapping("/gotoLeaveProcessList")
    public String gotoLeaveProcessList(){
        return "activitimanage/leaveProcess/leaveProcessList";

    }
    @RequestMapping("/gotoLeaveProcessEdit")
    public String gotoLeaveProcessEdit(@ModelAttribute("editFlag")int editFlag,Long leaveId,Model model){
        //进入修改页面需要将用户明细查询出来返回给页面显示
        if(editFlag==2){
            LeaveBean leaveBean=leaveBeanService.getLeaveBeanById(leaveId);
            leaveBean.setLeaveDate(new Date());
            model.addAttribute("leaveBean",leaveBean);
        }
        return "activitimanage/leaveProcess/leaveProcessEdit";
    }
    @RequestMapping("/getLeaveProcessList")
    public @ResponseBody Map<String,Object>getLeaveProcessList(){
        //返回对象
        Map<String,Object>resultMap=new HashMap<>();
        List<LeaveBean>leaveBeanList=new ArrayList<>();
        LeaveBean leaveBean=new LeaveBean();
        leaveBean.setLeaveUserId(UserUtils.getCurrentUserId());
        leaveBeanList=leaveBeanService.getLeaveBeanList(leaveBean);
        resultMap.put("leaveBeanList",leaveBeanList);
        return resultMap;

    }
    @RequestMapping("/saveLeaveProcess")
    public @ResponseBody Map<String,Object>saveLeaveProcess(@RequestBody  LeaveBean leaveBean){
        Map<String,Object>resultMap=new HashMap<>();
        try{
        if(leaveBean.getLeaveId()!=null){//修改
            leaveBeanService.updateLeaveBean(leaveBean);
            resultMap.put("result","修改请假单成功");
        }else{
            leaveBeanService.addLeaveBean(leaveBean);
            resultMap.put("result","请假单成功");
        }
        }catch (Exception e){
            logger.error("操作请假单失败",e);
            resultMap.put("result","操作请假单失败");
        }
        return resultMap;
    }
    @RequestMapping("/delLeaveProcess")
    public@ResponseBody Map<String,Object>delLeaveProcess(Long leaveId){
        Map<String,Object>resultMap=new HashMap<>();
        try{
            if(leaveBeanService.delLeaveBean(leaveId)){
                resultMap.put("result","删除请假单成功");
            }
        }catch (Exception e){
            logger.error("删除请假单失败",e);
            resultMap.put("resultMap","删除请假单失败");
        }
        return resultMap;
    }
    @RequestMapping("/doLeaveProcess")
    public @ResponseBody Map<String,Object>doLeaveProcess(Long leaveId){
        Map<String,Object>resultMap=new HashMap<>();
        try{
            leaveBeanService.doleaveProcess(leaveId);
            resultMap.put("result","申请请假成功,请转往任务处理功能提交请假单");
        }catch (Exception e){
            logger.error("申请请假失败",e);
            resultMap.put("result","申请请假失败");
        }
        return resultMap;

    }


}
