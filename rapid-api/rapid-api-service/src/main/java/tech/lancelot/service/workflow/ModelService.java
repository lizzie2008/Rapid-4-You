package tech.lancelot.service.workflow;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author lancelot
 */
public interface ModelService {
    /**
     * 模型列表
     *
     * @return
     */
    List<Model> list();

    /**
     * 创建模型
     *
     * @return
     */
    String create() throws UnsupportedEncodingException;

    /**
     * 部署模型
     */
    void deploy(String id) throws IOException;

    /**
     * 获取编辑器中对象Json
     * @param id
     * @return
     */
    ObjectNode getEditorJson(String id);

    /**
     * 保存模型
     * @param modelId
     * @param values
     */
    void saveModel(String modelId, MultiValueMap<String, String> values);

    /***
     * 获取本地化资源
     * @return
     */
    String getStencilset();
}
