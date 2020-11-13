package tech.lancelot.dto.blog;

import lombok.Data;
import tech.lancelot.annotations.Query;

import java.io.Serializable;

/**
 * @author lancelot
 */
@Data
public class ArchiveQueryCriteria implements Serializable {

    @Query(blurry = "name")
    private String blurry;
}
