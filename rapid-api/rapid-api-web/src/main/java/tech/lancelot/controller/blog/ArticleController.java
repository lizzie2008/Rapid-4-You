package tech.lancelot.controller.blog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.lancelot.annotations.DataPermission;
import tech.lancelot.annotations.Log;
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.system.User;
import tech.lancelot.dto.blog.ArticleQueryCriteria;
import tech.lancelot.service.blog.ArticleService;
import tech.lancelot.vo.Result;

/**
 * @author lancelot
 */
@Api(tags = "博客：文章管理")
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Log("博客|查询文章")
    @ApiOperation("博客|查询文章")
    @GetMapping
    @PreAuthorize("@el.check('article:list')")
    public Result query(ArticleQueryCriteria criteria, Pageable pageable) {
        return Result.success(articleService.queryAll(criteria, pageable));
    }

    @Log("博客|文章详情")
    @ApiOperation("博客|文章详情")
    @GetMapping(value = "/{id}")
    @AnonymousGetMapping
    public Result get(@PathVariable String id){
        return Result.success(articleService.findById(id));
    }

    @Log("博客|文章新增")
    @ApiOperation("博客|文章新增")
    @PostMapping
    @PreAuthorize("@el.check('article:add')")
    public Result create(@Validated @RequestBody Article resources) {
        articleService.create(resources);
        return Result.success();
    }

    @Log("博客|文章修改")
    @ApiOperation("博客|文章修改")
    @PutMapping
    @AnonymousGetMapping
//    @PreAuthorize("@el.check('article:edit')")
    public Result update(@Validated @RequestBody Article resources) {
        articleService.update(resources);
        return Result.success();
    }
}
