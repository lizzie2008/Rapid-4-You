package tech.lancelot.mapstruct.blog;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.blog.Tag;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.mapstruct.sys.BaseMapper;

/**
 * @author lancelot
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ArchiveMapper.class, Tag.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper extends BaseMapper<ArticleDto, Article> {
}
