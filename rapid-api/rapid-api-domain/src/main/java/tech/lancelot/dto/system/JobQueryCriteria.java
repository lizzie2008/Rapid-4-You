package tech.lancelot.dto.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import tech.lancelot.annotations.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lancelot
 */
@Data
@NoArgsConstructor
public class JobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}