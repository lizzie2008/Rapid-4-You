package tech.lancelot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.lancelot.exceptions.BadRequestException;
import tech.lancelot.exceptions.EntityExistException;
import tech.lancelot.exceptions.EntityNotFoundException;
import tech.lancelot.utils.ThrowableUtil;
import tech.lancelot.vo.Result;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author lancelot
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.error(BAD_REQUEST.value(),e.getMessage());
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result badCredentialsException(BadCredentialsException e){
        // 打印堆栈信息
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        return Result.error(BAD_REQUEST.value(),e.getMessage());
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BadRequestException.class)
    public Result badRequestException(BadRequestException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.error(e.getStatus(),e.getMessage());
    }

    /**
     * 处理 EntityExist
     */
    @ExceptionHandler(value = EntityExistException.class)
    public Result entityExistException(EntityExistException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.error(BAD_REQUEST.value(),e.getMessage());
    }

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public Result entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.error(NOT_FOUND.value(),e.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if(msg.equals(message)){
            message = str[1] + ":" + message;
        }
        return Result.error(BAD_REQUEST.value(),e.getMessage());
    }
}
