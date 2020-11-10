package tech.lancelot.mapstruct.blog;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tech.lancelot.domain.blog.Archive;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.dto.blog.ArchiveDto;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.mapstruct.sys.BaseMapper;

/**
 * @author lancelot
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArchiveMapper extends BaseMapper<ArchiveDto, Archive> {
}
