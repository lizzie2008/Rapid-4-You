package tech.lancelot.dto.blog;

import lombok.Data;
import tech.lancelot.annotations.Query;
import tech.lancelot.enums.BlogType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author lancelot
 */
@Data
public class CategoryQueryCriteria implements Serializable {

    @Query(blurry = "name")
    private String blurry;
}
