package tech.lancelot.repository.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tech.lancelot.domain.blog.Article;

import java.util.Set;

/**
 * @author lancelot
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {


    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<String> ids);
}
