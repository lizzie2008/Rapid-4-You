package tech.lancelot.controller.workflow;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousDeleteMapping;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.annotations.restful.AnonymousPostMapping;
import tech.lancelot.vo.workflow.HistoricProcessInstanceVo;
import tech.lancelot.vo.workflow.ProcessInstanceVo;
import tech.lancelot.vo.workflow.ProcessVo;
import tech.lancelot.vo.Result;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lancelot
 */
@Api(tags = "工作流：流程管理")
@RestController
@RequestMapping("/api/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    @Log("工作流|流程列表")
    @ApiOperation("工作流|流程列表")
    @GetMapping
    public Result list() {
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
        return Result.success(result);
    }

    @Log("工作流|启动流程")
    @ApiOperation("工作流|启动流程")
    @PostMapping("/start/{id}")
    public Result start(@PathVariable("id") String processDefinitionId) {
        runtimeService.startProcessInstanceById(processDefinitionId);
        return Result.success();
    }

    @Log("工作流|获取流程图")
    @ApiOperation("工作流|获取流程图")
    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable(value = "id") String processDefinitionId) throws IOException {
        //根据流程定义获取输入流
        InputStream is = repositoryService.getProcessDiagram(processDefinitionId);
        byte[] bytes = new byte[is.available()];
        is.read(bytes, 0, is.available());

        return bytes;
    }

    @Log("工作流|删除部署")
    @ApiOperation("工作流|删除部署")
    @DeleteMapping(value = "/delete/{id}")
    public Result delete(@PathVariable(value = "id") String deploymentId) {
        // 按照部署id进行删除
        repositoryService.deleteDeployment(deploymentId, true);
        return Result.success();
    }

    @Log("工作流|运行流程列表")
    @ApiOperation("工作流|运行流程列表")
    @GetMapping("/currentList")
    public Result currentList() {
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
        return Result.success(result);
    }

    @Log("工作流|历史流程列表")
    @ApiOperation("工作流|历史流程列表")
    @GetMapping("/historyList")
    public Result historyList() {
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
        return Result.success(result);
    }

    @Log("工作流|运行流程详情")
    @ApiOperation("工作流|运行流程详情")
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable(value = "id") String processInstanceId) {
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();
        return Result.success(historicActivityInstances);
    }
}
