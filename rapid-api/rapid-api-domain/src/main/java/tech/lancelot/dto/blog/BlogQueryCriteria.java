package tech.lancelot.dto.blog;

import lombok.Data;
import tech.lancelot.annotations.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lancelot
 */
@Data
public class BlogQueryCriteria {

    @Query(blurry = "title,summary")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
