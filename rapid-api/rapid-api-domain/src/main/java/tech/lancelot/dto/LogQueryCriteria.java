package tech.lancelot.dto;

import lombok.Data;
import tech.lancelot.annotations.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lancelot
 */
@Data
public class LogQueryCriteria {

    @Query(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    @Query
    private String logType;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}

