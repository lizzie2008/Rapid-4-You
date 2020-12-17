package tech.lancelot.vo.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.bpmn.data.IOSpecification;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lancelot
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessVo {

    private String id;
    private String name;
    private String key;
    private int version;
    private String deploymentId;
    private String diagramResourceName;
}
