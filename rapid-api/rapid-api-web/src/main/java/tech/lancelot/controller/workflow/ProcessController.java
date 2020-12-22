package tech.lancelot.controller.workflow;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.history.HistoricActivityInstance;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.service.workflow.ProcessService;
import tech.lancelot.vo.Result;
import tech.lancelot.vo.workflow.HistoricProcessInstanceVo;
import tech.lancelot.vo.workflow.ProcessInstanceVo;
import tech.lancelot.vo.workflow.ProcessVo;

import java.io.IOException;
import java.util.List;

/**
 * @author lancelot
 */
@Api(tags = "工作流：流程管理")
@RestController
@RequestMapping("/api/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @Log("工作流|流程列表")
    @ApiOperation("工作流|流程列表")
    @GetMapping
    public Result list() {
        List<ProcessVo> list = processService.list();
        return Result.success(list);
    }

    @Log("工作流|启动流程")
    @ApiOperation("工作流|启动流程")
    @PostMapping("/start/{id}")
    public Result start(@PathVariable("id") String processDefinitionId) {
        processService.start(processDefinitionId);
        return Result.success();
    }

    @Log("工作流|获取流程图")
    @ApiOperation("工作流|获取流程图")
    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @AnonymousGetMapping
    public byte[] getImage(@PathVariable(value = "id") String processDefinitionId) throws IOException {
        byte[] image = processService.getImage(processDefinitionId);
        return image;
    }

    @Log("工作流|删除部署")
    @ApiOperation("工作流|删除部署")
    @DeleteMapping(value = "/delete/{id}")
    public Result delete(@PathVariable(value = "id") String deploymentId) {
        processService.delete(deploymentId);
        return Result.success();
    }

    @Log("工作流|运行流程列表")
    @ApiOperation("工作流|运行流程列表")
    @GetMapping("/currentList")
    public Result currentList() {
        List<ProcessInstanceVo> processInstanceVos = processService.currentList();
        return Result.success(processInstanceVos);
    }

    @Log("工作流|历史流程列表")
    @ApiOperation("工作流|历史流程列表")
    @GetMapping("/historyList")
    public Result historyList() {
        List<HistoricProcessInstanceVo> historicProcessInstanceVos = processService.historyList();
        return Result.success(historicProcessInstanceVos);
    }

    @Log("工作流|运行流程详情")
    @ApiOperation("工作流|运行流程详情")
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable(value = "id") String processInstanceId) {
        List<HistoricActivityInstance> detail = processService.detail(processInstanceId);
        return Result.success(detail);
    }
}
