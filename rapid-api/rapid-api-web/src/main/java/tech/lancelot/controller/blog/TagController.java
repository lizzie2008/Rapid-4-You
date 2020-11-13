package tech.lancelot.controller.blog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.dto.blog.CategoryQueryCriteria;
import tech.lancelot.dto.blog.TagQueryCriteria;
import tech.lancelot.service.blog.CategoryService;
import tech.lancelot.service.blog.TagService;
import tech.lancelot.vo.Result;

/**
 * @author lancelot
 */
@Api(tags = "博客：标签管理")
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Log("博客|查询标签")
    @ApiOperation("博客|查询标签")
    @GetMapping
    @AnonymousGetMapping
    public Result getAll() {
        return Result.success(tagService.getAll());
    }
}
