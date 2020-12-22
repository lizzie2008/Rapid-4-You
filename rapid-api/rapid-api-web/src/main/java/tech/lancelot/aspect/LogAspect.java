package tech.lancelot.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import tech.lancelot.domain.system.Log;
import tech.lancelot.service.system.LogService;
import tech.lancelot.utils.RequestHolder;
import tech.lancelot.utils.SecurityUtils;
import tech.lancelot.utils.StringUtils;
import tech.lancelot.utils.ThrowableUtil;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lancelot
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    ThreadLocal<Long> currentTime = new ThreadLocal<>();
    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(tech.lancelot.annotations.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Log log = new Log("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint) joinPoint, log);
    }

    public String getUsername() {
        try {
            return SecurityUtils.getCurrentUsername();
        } catch (Exception e) {
            return "";
        }
    }
}
