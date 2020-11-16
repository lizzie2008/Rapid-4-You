package tech.lancelot.repository.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.blog.Category;

/**
 * @author lancelot
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {

}
