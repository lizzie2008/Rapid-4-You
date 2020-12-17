package tech.lancelot.vo.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.task.DelegationState;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lancelot
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskVo {

    private String id;
    private String name;
    private String assignee;
    private String taskDefinitionKey;
    private String processDefinitionId;
    private Date createTime;

}
