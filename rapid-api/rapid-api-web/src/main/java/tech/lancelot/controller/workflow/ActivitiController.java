package tech.lancelot.controller.workflow;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.annotations.restful.AnonymousPostMapping;
import tech.lancelot.service.blog.TagService;
import tech.lancelot.service.workflow.ActivitiService;
import tech.lancelot.vo.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lancelot
 */
//@Api(tags = "工作流：接口")
//@RestController
//@RequestMapping("/api/activiti")
//@RequiredArgsConstructor
public class ActivitiController {

//    private final ActivitiService activitiService;
//
//    @PostMapping(value = "/process")
//    @AnonymousPostMapping
//    public void startProcessInstance() {
//        activitiService.startProcess();
//    }
//
//    @GetMapping(value = "/tasks")
//    @AnonymousGetMapping
//    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
//        List<Task> tasks = activitiService.getTasks(assignee);
//        List<TaskRepresentation> dtos = new ArrayList<>();
//        for (Task task : tasks) {
//            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
//        }
//        return dtos;
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class TaskRepresentation {
//        private String id;
//        private String name;
//    }
}
