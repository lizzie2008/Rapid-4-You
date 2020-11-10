package tech.lancelot.service.blog;

import org.springframework.data.domain.Pageable;
import tech.lancelot.dto.blog.CategoryDto;
import tech.lancelot.dto.blog.CategoryQueryCriteria;
import tech.lancelot.dto.blog.TagDto;
import tech.lancelot.dto.blog.TagQueryCriteria;

import java.util.List;

/**
 * @author lancelot
 */
public interface TagService {

    /**
     * 查询全部
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(TagQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部不分页
     * @param criteria 条件
     * @return /
     */
    List<TagDto> queryAll(TagQueryCriteria criteria);
}
