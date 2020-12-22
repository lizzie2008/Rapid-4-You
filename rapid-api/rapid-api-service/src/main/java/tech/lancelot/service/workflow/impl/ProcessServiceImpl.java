package tech.lancelot.service.workflow.impl;

import lombok.RequiredArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import tech.lancelot.service.workflow.ProcessService;
import tech.lancelot.vo.workflow.HistoricProcessInstanceVo;
import tech.lancelot.vo.workflow.ProcessInstanceVo;
import tech.lancelot.vo.workflow.ProcessVo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    @Override
    public List<ProcessVo> list() {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        List<ProcessVo> result = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitions) {
            ProcessVo processVo = new ProcessVo();
            processVo.setId(processDefinition.getId());
            processVo.setName(processDefinition.getName());
            processVo.setKey(processDefinition.getKey());
            processVo.setVersion(processDefinition.getVersion());
            processVo.setDeploymentId(processDefinition.getDeploymentId());
            processVo.setDiagramResourceName(processDefinition.getDiagramResourceName());

            result.add(processVo);
        }
        return result;
    }

    @Override
    public void start(String processDefinitionId) {
        runtimeService.startProcessInstanceById(processDefinitionId);
    }

    @Override
    public byte[] getImage(String processDefinitionId) throws IOException {
        //根据流程定义获取输入流
        InputStream is = repositoryService.getProcessDiagram(processDefinitionId);
        byte[] bytes = new byte[is.available()];
        is.read(bytes, 0, is.available());

        return bytes;
    }

    @Override
    public void delete(String deploymentId) {
        // 按照部署id进行删除
        repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public List<ProcessInstanceVo> currentList() {
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
        List<ProcessInstanceVo> result = new ArrayList<>();
        for (ProcessInstance processInstance : processInstances) {
            ProcessInstanceVo processInstanceVo = new ProcessInstanceVo();
            processInstanceVo.setId(processInstance.getId());
            processInstanceVo.setStartTime(processInstance.getStartTime());
            processInstanceVo.setProcessDefinitionId(processInstance.getProcessDefinitionId());
            processInstanceVo.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
            processInstanceVo.setProcessDefinitionName(processInstance.getProcessDefinitionName());
            processInstanceVo.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
            processInstanceVo.setDeploymentId(processInstance.getDeploymentId());
            result.add(processInstanceVo);
        }
        return result;
    }

    @Override
    public List<HistoricProcessInstanceVo> historyList() {
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().finished().list();
        List<HistoricProcessInstanceVo> result = new ArrayList<>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            HistoricProcessInstanceVo processInstanceVo = new HistoricProcessInstanceVo();
            processInstanceVo.setId(historicProcessInstance.getId());
            processInstanceVo.setStartTime(historicProcessInstance.getStartTime());
            processInstanceVo.setEndTime(historicProcessInstance.getEndTime());
            processInstanceVo.setStartActivityId(historicProcessInstance.getStartActivityId());
            processInstanceVo.setEndActivityId(historicProcessInstance.getEndActivityId());
            processInstanceVo.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
            processInstanceVo.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
            processInstanceVo.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
            processInstanceVo.setProcessDefinitionVersion(historicProcessInstance.getProcessDefinitionVersion());
            processInstanceVo.setDeploymentId(historicProcessInstance.getDeploymentId());
            result.add(processInstanceVo);
        }
        return result;
    }

    @Override
    public List<HistoricActivityInstance> detail(String processInstanceId) {
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();
        return historicActivityInstances;
    }
}