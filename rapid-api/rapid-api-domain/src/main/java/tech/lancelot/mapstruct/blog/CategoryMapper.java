package tech.lancelot.mapstruct.blog;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.blog.Category;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.dto.blog.CategoryDto;
import tech.lancelot.mapstruct.sys.BaseMapper;
import tech.lancelot.mapstruct.sys.MenuMapper;

/**
 * @author lancelot
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper extends BaseMapper<CategoryDto, Category> {
}
