package com.oa.sys.activitimanage.service.impl;

import com.oa.sys.activitimanage.service.ILeaveBeanService;
import com.oa.sys.activitimanage.service.IWorkFlowService;
import com.oa.sys.entity.LeaveBean;
import com.oa.sys.mapper.LeaveBeanMapper;
import com.oa.sys.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class LeaveBeanService implements ILeaveBeanService {

    @Autowired
    private LeaveBeanMapper leaveBeanMapper;

    @Autowired
    private IWorkFlowService workFlowService;
    @Override
    public List<LeaveBean> getLeaveBeanList(LeaveBean leaveBean) {
        return leaveBeanMapper.getLeaveBeanList(leaveBean);
    }

    @Override
    public LeaveBean getLeaveBeanById(Long leaveId) {
        return leaveBeanMapper.getLeaveBeanById(leaveId);
    }

    @Override
    public boolean addLeaveBean(LeaveBean leaveBean) {
        //加入录入人员的id
        //初始化状态的录入
        leaveBean.setLeaveUserId(UserUtils.getCurrentUserId());
        leaveBean.setLeaveState(0);
        return leaveBeanMapper.addLeaveBean(leaveBean);
    }

    @Override
    public boolean delLeaveBean(Long leaveId) {
        return leaveBeanMapper.delLeaveBean(leaveId);
    }

    @Override
    public boolean updateLeaveBeanState(Long leaveId, Integer state) {
        return leaveBeanMapper.updateLeaveBeanState(leaveId,state);
    }

    @Override
    public boolean updateLeaveBean(LeaveBean leaveBean) {
        return leaveBeanMapper.updateLeaveBean(leaveBean);
    }

    /*
     *请假单录入以后,根据流程图,我们要启动流程
     *启动流程我们需要做一下几件事情
     *	1:获取请假记录,更改请假单状态(流程一旦启动,请假单信息是不能再次编辑)
     *  2:启动流程需要流程定义的key--LeaveBean
     *  3:根据流程定义的key启动流程实例
     *  	1>将表单数据与流程实例关联
     *  		两种方式:
     *  				a:将leaveBean对象以流程变量的方式存储
     *  				b:将请假单与流程实例产生一个对应关系(利用act_ru_execution的business_key_字段)
     *  					格式随意:但是必须包括两个内容(流程定义的key+"*"+leaveId)
     *  	2> 获取当前的操作用户,设置下一步处理人的流程变量
     *      3> 启动流程变量
     *   4:加上事务
     *
     */

    @Override
    public void doleaveProcess(Long leaveId) {
    //获取请假记录，更改请假单状态
        leaveBeanMapper.updateLeaveBeanState(leaveId,1);
        //获取流程定义的key
        LeaveBean leaveBean =getLeaveBeanById(leaveId);
        String leaveProcessKey=leaveBean.getClass().getSimpleName();
        //构造businessKey --将本次的请假单对象与流程实例产生一个对应关系
        String businessKey=leaveProcessKey+"."+leaveId;

        //构造下一步处理人的流程变量
        Map<String,Object>variables=new HashMap<>();
        variables.put("userId",UserUtils.getCurrentUserId());
        //启动流程变量
        workFlowService.startProcess(leaveProcessKey,businessKey,variables);
    }
















}
