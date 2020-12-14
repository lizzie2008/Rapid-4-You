package tech.lancelot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.utils.SpringContextHolder;

/**
 * @author lancelot
 */
@EnableAsync
@ApiIgnore
@RestController
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableTransactionManagement
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppRun {

    public static void main(String[] args) {
        //同时整合 Redis 和 ES 时，解决冲突
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(AppRun.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    /**
     * 访问首页提示
     *
     * @return /
     */
    @AnonymousGetMapping("/")
    public String index() {
        return "Backend service started successfully";
    }
}
