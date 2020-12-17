package tech.lancelot.vo.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lancelot
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceVo {
    private String id;
    private Date startTime;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private Integer processDefinitionVersion;
    private String deploymentId;
}
