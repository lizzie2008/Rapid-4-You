package tech.lancelot.controller.blog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.domain.system.User;
import tech.lancelot.dto.blog.ArchiveQueryCriteria;
import tech.lancelot.service.blog.ArchiveService;
import tech.lancelot.vo.Result;

/**
 * @author lancelot
 */
@Api(tags = "博客：归档管理")
@RestController
@RequestMapping("/api/archives")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;

    @Log("博客|查询归档")
    @ApiOperation("博客|查询归档")
    @GetMapping
    @AnonymousGetMapping
    public Result getAll() {
        return Result.success(archiveService.getAll());
    }
}
