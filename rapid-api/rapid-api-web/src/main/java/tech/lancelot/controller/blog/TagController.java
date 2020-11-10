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
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Log("查询博客|标签")
    @ApiOperation("查询博客|标签")
    @GetMapping
    @AnonymousGetMapping
    public Result queryAll(TagQueryCriteria criteria, Pageable pageable) {
        return Result.success(tagService.queryAll(criteria,pageable));
    }
}
