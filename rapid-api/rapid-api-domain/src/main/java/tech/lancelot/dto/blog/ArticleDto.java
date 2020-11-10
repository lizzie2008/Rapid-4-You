package tech.lancelot.dto.blog;

import lombok.Getter;
import lombok.Setter;
import tech.lancelot.domain.blog.Archive;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.blog.Category;
import tech.lancelot.domain.blog.Tag;
import tech.lancelot.enums.BlogType;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author lancelot
 */
@Getter
@Setter
public class ArticleDto {

    private String id;

    private BlogType blogType;

    private Boolean isSticky ;

    private String title;

    private String image;

    private String summary;

    private String content;

    private Timestamp createTime;

    private Integer readSize = 0;

    private Integer commentSize = 0;

    private Integer likeSize = 0;

    private Category category;

    private Archive archive;

    private Set<Tag> tags;
}
