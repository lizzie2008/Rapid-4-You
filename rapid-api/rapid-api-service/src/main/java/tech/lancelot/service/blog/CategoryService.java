package tech.lancelot.service.blog;

import org.springframework.data.domain.Pageable;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.dto.blog.ArticleQueryCriteria;
import tech.lancelot.dto.blog.CategoryDto;
import tech.lancelot.dto.blog.CategoryQueryCriteria;

import java.util.List;

/**
 * @author lancelot
 */
public interface CategoryService {

    /**
     * 查询全部
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(CategoryQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部不分页
     * @return /
     */
    List<CategoryDto> getAll();
}
