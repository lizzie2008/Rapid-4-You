package tech.lancelot.repository.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.lancelot.domain.blog.Article;

/**
 * @author lancelot
 */
public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

}
