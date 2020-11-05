package tech.lancelot.service.blog;

import org.springframework.data.domain.Pageable;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.dto.blog.BlogQueryCriteria;

import java.util.List;

/**
 * @author lancelot
 */
public interface ArticleService {

    /**
     * 查询全部
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(BlogQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部不分页
     * @param criteria 条件
     * @return /
     */
    List<ArticleDto> queryAll(BlogQueryCriteria criteria);
}
