package tech.lancelot.controller.workflow;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.annotations.restful.AnonymousPostMapping;
import tech.lancelot.vo.Result;
import tech.lancelot.vo.workflow.TaskVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lancelot
 */
@Api(tags = "工作流：任务管理")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final FormService formService;


    @Log("工作流|任务列表")
    @ApiOperation("工作流|任务列表")
    @GetMapping()
    @AnonymousGetMapping
    public Result list() {
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

    @Log("工作流|获取任务表单")
    @ApiOperation("工作流|获取任务表单")
    @GetMapping("/getFormProperties/{id}")
    @AnonymousGetMapping
    public Result getFormProperties(@PathVariable(value = "id") String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        return Result.success(taskFormData.getFormProperties());
    }

    @Log("工作流|完成任务")
    @ApiOperation("工作流|完成任务")
    @PostMapping("/complete/{id}")
    @AnonymousPostMapping
    public Result complete(@PathVariable(value = "id") String taskId, @RequestBody Map<String, Object> variables) {
        taskService.complete(taskId,variables);
        return Result.success();
    }
}
