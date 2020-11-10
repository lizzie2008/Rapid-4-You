package tech.lancelot.dto.blog;

import lombok.Getter;
import lombok.Setter;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.blog.Category;

import java.sql.Timestamp;

/**
 * @author lancelot
 */
@Getter
@Setter
public class ArchiveDto {

    private Long id;

    private  String name;
}
