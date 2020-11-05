package tech.lancelot.dto.sys;

import lombok.Data;
import tech.lancelot.annotations.Query;

@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name",joinName = "dict")
    private String dictName;
}