package tech.lancelot.service.blog;

import org.springframework.data.domain.Pageable;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.system.User;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.dto.blog.ArticleQueryCriteria;
import tech.lancelot.dto.system.MenuDto;

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
    Object queryAll(ArticleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部不分页
     * @param criteria 条件
     * @return /
     */
    List<ArticleDto> queryAll(ArticleQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    ArticleDto findById(String id);

    /**
     * 新增
     * @param resources /
     */
    void create(Article resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(Article resources);
}
