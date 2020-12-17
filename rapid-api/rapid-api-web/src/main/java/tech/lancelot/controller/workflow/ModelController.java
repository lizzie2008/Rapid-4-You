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
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.annotations.restful.AnonymousPostMapping;
import tech.lancelot.service.workflow.ModelService;
import tech.lancelot.vo.Result;

import java.io.IOException;
import java.util.List;

/**
 * @author lancelot
 */
@Api(tags = "工作流：模型管理")
@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @Log("工作流|模型列表")
    @ApiOperation("工作流|模型列表")
    @GetMapping
    @AnonymousGetMapping
    public Result list() {
        List<Model> models = modelService.list();
        return Result.success(models);
    }

    @Log("工作流|模型新增")
    @ApiOperation("工作流|模型新增")
    @PostMapping
    @AnonymousPostMapping
    public Result create() throws IOException {
        String id = modelService.create();
        return Result.success(id);
    }

    @Log("工作流|模型部署")
    @ApiOperation("工作流|模型部署")
    @PostMapping("/deploy/{id}")
    @AnonymousPostMapping
    public Result deploy(@PathVariable("id") String id) throws Exception {
        modelService.deploy(id);
        return Result.success();
    }
}
