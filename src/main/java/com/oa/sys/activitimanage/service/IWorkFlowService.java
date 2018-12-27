package com.oa.sys.activitimanage.service;

import javafx.concurrent.Task;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IWorkFlowService {
    /**
     * 获取所有的流程部署对象
     * @return
     */

    public List<Deployment> getDeployments();

    /**
     * 执行流程部署（zip格式）
     * @param inpuStream
     * @param deploymentName
     * @return
     */
    public Deployment addDeployment(InputStream inpuStream,String deploymentName);

    /**
     * 删除部署流程信息
     * @param deploymentId
     * @param flag
     */
    public void delDeployment(String deploymentId,boolean flag);

    /**
     * 获取所有的流程定义信息
     * @return
     */
    public List<ProcessDefinition>getProcessDefinitions();

    /**
     * 根据部署id和流程图的名称获取流程图的inputStream
     * @return
     */

    public InputStream getProcessDefinitionImageStream(String deploymentId,String imageName);

    /**
     * 根据流程定义的key和业务关系key和流程变量启动流程
     * @param leaveProcessKey
     * @param businessKey
     * @param variables
     * @return
     */
    public ProcessInstance startProcess(String leaveProcessKey, String businessKey, Map<String, Object> variables);




    /**
     * 根据当前用户id查询当前用户的代办任务
     * @Title: getTaskList
     * @Description: TODO
     * @param assingnee
     * @return
     */
    public List<Task>getTaskList(String assingnee);
}
