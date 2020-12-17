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
public class HistoricProcessInstanceVo {
    private String id;
    private Date startTime;
    private Date endTime;
    private String startActivityId;
    private String endActivityId;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private Integer processDefinitionVersion;
    private String deploymentId;
}
