package tech.lancelot.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.domain.system.Job;
import tech.lancelot.dto.system.JobQueryCriteria;
import tech.lancelot.dto.system.LogQueryCriteria;
import tech.lancelot.exceptions.BadRequestException;
import tech.lancelot.service.system.JobService;
import tech.lancelot.service.system.LogService;
import tech.lancelot.utils.SecurityUtils;
import tech.lancelot.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author lancelot
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：日志管理")
@RequestMapping("/api/log")
public class LogController {

    private final LogService logService;

    @ApiOperation("用户日志查询")
    @GetMapping(value = "/currentUser")
    public Result queryUserLog(LogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        criteria.setBlurry(SecurityUtils.getCurrentUsername());
        return Result.success(logService.queryAllByUser(criteria, pageable));
    }
}