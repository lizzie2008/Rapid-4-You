package tech.lancelot.dto.blog;

import lombok.Getter;
import lombok.Setter;
import tech.lancelot.domain.blog.Article;

import java.sql.Timestamp;

/**
 * @author lancelot
 */
@Getter
@Setter
public class ArticleDto {

    private String id;

    private Article.BlogType blogType;

    private Boolean isSticky ;

    private String title;

    private String image;

    private String summary;

    private String content;

    private Timestamp createTime;

    private Integer readSize = 0;

    private Integer commentSize = 0;

    private Integer likeSize = 0;
}
