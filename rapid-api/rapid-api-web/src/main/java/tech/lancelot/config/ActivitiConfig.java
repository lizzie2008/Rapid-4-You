package tech.lancelot.config;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lancelot
 */
@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

//    @Bean
//    public CommandLineRunner init(final RepositoryService repositoryService,
//                                  final RuntimeService runtimeService,
//                                  final TaskService taskService) {
//
//        return strings -> {
//            System.out.println("Number of process definitions : "
//                    + repositoryService.createProcessDefinitionQuery().count());
//            System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
//            runtimeService.startProcessInstanceByKey("oneTaskProcess");
//            System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
//        };
//
//    }

    /**
     * 解決工作流生成图片乱码问题
     *
     * @param processEngineConfiguration processEngineConfiguration
     */
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
    }
}
