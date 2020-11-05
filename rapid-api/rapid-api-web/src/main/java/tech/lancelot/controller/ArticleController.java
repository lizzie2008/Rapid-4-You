package tech.lancelot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.AnonymousAccess;
import tech.lancelot.annotations.Log;
import tech.lancelot.dto.blog.BlogQueryCriteria;
import tech.lancelot.service.blog.ArticleService;
import tech.lancelot.vo.Result;

/**
 * @author lancelot
 */
@Api(tags = "博客：文章管理")
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Log("查询文章")
    @ApiOperation("查询文章")
    @GetMapping
    @AnonymousAccess
//    @PreAuthorize("@el.check('blog:list')")
    public Result query(BlogQueryCriteria criteria, Pageable pageable) {
        return Result.success(articleService.queryAll(criteria, pageable));
    }
}
