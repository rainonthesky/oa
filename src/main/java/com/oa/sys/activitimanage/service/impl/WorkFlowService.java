package com.oa.sys.activitimanage.service.impl;

import com.oa.sys.activitimanage.service.IWorkFlowService;
import javafx.concurrent.Task;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class WorkFlowService implements IWorkFlowService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Override
    public List<Deployment> getDeployments() {
        return repositoryService.createDeploymentQuery()
                .orderByDeploymenTime()
                .desc()
                .list();
    }

    @Override
    public Deployment addDeployment(InputStream inputStream, String deploymentName) {
        Deployment deployment=repositoryService.createDeployment()
                .addZipInputStream(new ZipInputStream(inputStream)).name(deploymentName).deploy();
        return deployment;
    }

    @Override
    public void delDeployment(String deploymentId, boolean flag) {
        repositoryService.deleteDeployment(deploymentId, flag);
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitions() {
        return repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionKey().orderByProcessDefinitionVersion().desc().list();
    }

    @Override
    public InputStream getProcessDefinitionImageStream(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId,imageName);
    }

    @Override
    public ProcessInstance startProcess(String leaveProcessKey, String businessKey, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceByKey(leaveProcessKey,businessKey,variables);
    }

    @Override
    public List<Task> getTaskList(String assingnee) {
        return null;
    }
}


















