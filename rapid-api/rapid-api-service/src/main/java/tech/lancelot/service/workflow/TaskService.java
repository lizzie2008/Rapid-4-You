package tech.lancelot.service.workflow;

import org.activiti.engine.form.FormProperty;
import tech.lancelot.vo.workflow.TaskVo;

import java.util.List;
import java.util.Map;

/**
 * @author lancelot
 */
public interface TaskService {

    /**
     * 任务列表
     *
     * @return
     */
    List<TaskVo> list();

    /**
     * 获取任务表单
     *
     * @param taskId
     * @return
     */
    List<FormProperty> getFormProperties(String taskId);

    /**
     * 完成任务
     *
     * @param taskId
     * @param variables
     */
    void complete(String taskId, Map<String, Object> variables);
}
