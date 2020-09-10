package tech.lancelot.dto;

import lombok.Data;
import tech.lancelot.annotations.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lancelot
 */
@Data
public class MenuQueryCriteria {

    @Query(blurry = "title,component,permission")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query(type = Query.Type.IS_NULL, propName = "pid")
    private Boolean pidIsNull;

    @Query
    private Long pid;
}
