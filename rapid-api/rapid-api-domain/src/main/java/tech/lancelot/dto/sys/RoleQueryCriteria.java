package tech.lancelot.dto.sys;

import lombok.Data;
import tech.lancelot.annotations.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lancelot
 */
@Data
public class RoleQueryCriteria {

    @Query(blurry = "name,description")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
