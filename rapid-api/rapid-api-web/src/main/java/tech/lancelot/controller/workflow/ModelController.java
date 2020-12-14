package tech.lancelot.controller.workflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.annotations.restful.AnonymousPostMapping;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.service.workflow.ActivitiService;
import tech.lancelot.vo.Result;
import tech.lancelot.vo.TaskVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lancelot
 */
@Api(tags = "工作流：模型管理")
@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    private final ObjectMapper objectMapper;

    @Log("工作流|模型列表")
    @ApiOperation("工作流|模型列表")
    @GetMapping
    @AnonymousGetMapping
    public Result list() {
        List<Model> models = repositoryService.createModelQuery().orderByCreateTime().desc().list();
        return Result.success(models);
    }

    @Log("工作流|模型新增")
    @ApiOperation("工作流|模型新增")
    @PostMapping
    @AnonymousPostMapping
    public Result create() throws IOException {

        //初始化一个空模型
        Model model = repositoryService.newModel();

        //设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));

        return Result.success(id);
    }

    @Log("工作流|模型部署")
    @ApiOperation("工作流|模型部署")
    @PostMapping("/deploy/{id}")
    @AnonymousPostMapping
    public Result deploy(@PathVariable("id") String id) throws Exception {

        //获取模型
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            throw new RuntimeException("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            throw new RuntimeException("据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return Result.success();
    }

    @Log("工作流|启动流程")
    @ApiOperation("工作流|启动流程")
    @PostMapping("/start/{key}")
    @AnonymousPostMapping
    public Result start(@PathVariable("key") String key) throws Exception {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        return Result.success();
    }

    @Log("工作流|模型列表")
    @ApiOperation("工作流|模型列表")
    @GetMapping("/getTasks")
    @AnonymousGetMapping
    public Result getTasks() {
        List<Task> list = taskService.createTaskQuery().list();
        List<TaskVo> result = new ArrayList<>();
        for (Task task : list) {
            TaskVo taskVo = new TaskVo();
            taskVo.setId(task.getId());
            taskVo.setName(task.getName());
            taskVo.setAssignee(task.getAssignee());
            taskVo.setProcessDefinitionId(task.getProcessDefinitionId());
            taskVo.setTaskDefinitionKey(task.getTaskDefinitionKey());
            taskVo.setCreateTime(task.getCreateTime());

            result.add(taskVo);
        }
        return Result.success(result);
    }
}
