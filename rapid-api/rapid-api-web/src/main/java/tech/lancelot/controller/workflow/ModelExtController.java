package tech.lancelot.controller.workflow;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.service.workflow.ModelService;

/**
 * @author lancelot
 */
@RestController
@RequestMapping("/workflow/activiti")
@RequiredArgsConstructor
public class ModelExtController {

    private final ModelService modelService;

    @RequestMapping(value = "/model/{modelId}/json")
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        ObjectNode editorJson = modelService.getEditorJson(modelId);
        return editorJson;
    }

    @RequestMapping(value = "/model/{modelId}/save", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {
        modelService.saveModel(modelId, values);
    }

    @RequestMapping(value = "/editor/stencilset", produces = "application/json;charset=utf-8")
    public @ResponseBody
    String getStencilset() {
        String stencilset = modelService.getStencilset();
        return stencilset;
    }
}
