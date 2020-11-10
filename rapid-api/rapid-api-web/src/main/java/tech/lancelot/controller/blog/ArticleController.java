package tech.lancelot.controller.blog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.lancelot.annotations.Log;
import tech.lancelot.dto.blog.ArticleQueryCriteria;
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

    @Log("查询博客|文章")
    @ApiOperation("查询博客|文章")
    @GetMapping
    @PreAuthorize("@el.check('article:list')")
    public Result query(ArticleQueryCriteria criteria, Pageable pageable) {
        return Result.success(articleService.queryAll(criteria, pageable));
    }
}
