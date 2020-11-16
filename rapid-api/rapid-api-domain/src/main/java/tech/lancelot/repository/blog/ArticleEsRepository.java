package tech.lancelot.repository.blog;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import tech.lancelot.domain.blog.ArticleEs;

import java.util.Set;

/**
 * @author lancelot
 */
@Repository
public interface ArticleEsRepository extends ElasticsearchRepository<ArticleEs, String> {

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<String> ids);
}
