package tech.lancelot.dto.blog;

import lombok.Data;
import tech.lancelot.annotations.Query;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.blog.Category;
import tech.lancelot.enums.BlogType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lancelot
 */
@Data
public class ArticleQueryCriteria implements Serializable {

    @Query(blurry = "title")
    private String blurry;

    @Query
    private BlogType blogType;

    @Query(propName = "id", joinName = "category")
    private Long categoryId;

    @Query(propName = "id", type = Query.Type.IN, joinName = "tags")
    private Set<Long> tagIds = new HashSet<>();
}
