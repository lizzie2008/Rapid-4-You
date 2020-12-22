package tech.lancelot.service.workflow.impl;

import lombok.RequiredArgsConstructor;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import tech.lancelot.service.workflow.TaskService;
import tech.lancelot.vo.workflow.TaskVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final org.activiti.engine.TaskService taskService;
    private final FormService formService;

    @Override
    public List<TaskVo> list() {
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
        return result;
    }

    @Override
    public List<FormProperty> getFormProperties(String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        return taskFormData.getFormProperties();
    }

    @Override
    public void complete(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }
}