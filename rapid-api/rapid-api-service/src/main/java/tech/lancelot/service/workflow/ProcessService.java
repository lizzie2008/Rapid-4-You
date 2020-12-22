package tech.lancelot.service.workflow;

import org.activiti.engine.history.HistoricActivityInstance;
import tech.lancelot.vo.workflow.HistoricProcessInstanceVo;
import tech.lancelot.vo.workflow.ProcessInstanceVo;
import tech.lancelot.vo.workflow.ProcessVo;

import java.io.IOException;
import java.util.List;

/**
 * @author lancelot
 */
public interface ProcessService {
    /**
     * 流程列表
     *
     * @return
     */
    List<ProcessVo> list();

    /**
     * 启动流程
     *
     * @param processDefinitionId
     */
    void start(String processDefinitionId);

    /**
     * 获取流程图
     *
     * @param processDefinitionId
     * @return
     */
    byte[] getImage(String processDefinitionId) throws IOException;

    /**
     * 删除部署
     *
     * @param deploymentId
     */
    void delete(String deploymentId);

    /**
     * 运行流程列表
     *
     * @return
     */
    List<ProcessInstanceVo> currentList();

    /**
     * 历史流程列表
     *
     * @return
     */
    List<HistoricProcessInstanceVo> historyList();

    /**
     * 运行流程详情
     * @param processInstanceId
     * @return
     */
    List<HistoricActivityInstance> detail(String processInstanceId);
}
