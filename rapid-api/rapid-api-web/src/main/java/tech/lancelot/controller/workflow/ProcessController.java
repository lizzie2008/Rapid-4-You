package tech.lancelot.controller.workflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.annotations.restful.AnonymousPostMapping;
import tech.lancelot.vo.ProcessVo;
import tech.lancelot.vo.Result;
import tech.lancelot.vo.TaskVo;

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
    private final HistoryService historyService;

    @Log("工作流|流程列表")
    @ApiOperation("工作流|流程列表")
    @GetMapping
    @AnonymousGetMapping
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

    @Log("工作流|流程列表")
    @ApiOperation("工作流|流程列表")
    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @AnonymousGetMapping
    public byte[] getImage(@PathVariable(value = "id") String processInstanceId) throws IOException {
        //根据流程定义获取输入流
        InputStream is = repositoryService.getProcessDiagram(processInstanceId);
        byte[] bytes = new byte[is.available()];
        is.read(bytes, 0, is.available());

        return bytes;
    }


}
