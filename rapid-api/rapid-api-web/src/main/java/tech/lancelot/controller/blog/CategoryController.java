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
import tech.lancelot.annotations.restful.AnonymousGetMapping;
import tech.lancelot.dto.blog.ArticleQueryCriteria;
import tech.lancelot.dto.blog.CategoryQueryCriteria;
import tech.lancelot.service.blog.ArticleService;
import tech.lancelot.service.blog.CategoryService;
import tech.lancelot.vo.Result;

/**
 * @author lancelot
 */
@Api(tags = "博客：分类管理")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Log("博客|查询分类")
    @ApiOperation("博客|查询分类")
    @GetMapping
    @AnonymousGetMapping
    public Result getAll() {
        return Result.success(categoryService.getAll());
    }
}
